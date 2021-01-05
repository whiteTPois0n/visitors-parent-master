package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.entity.QVisit;
import ch.elca.visitors.persistence.entity.Visit;
import ch.elca.visitors.persistence.repository.AppointmentRepository;
import ch.elca.visitors.persistence.repository.ContactRepository;
import ch.elca.visitors.persistence.repository.VisitRepository;
import ch.elca.visitors.persistence.repository.VisitorRepository;
import ch.elca.visitors.service.dto.VisitDto;
import ch.elca.visitors.service.excel.VisitExporter;
import ch.elca.visitors.service.exception.ResourceNotFoundException;
import ch.elca.visitors.service.mapper.VisitMapper;
import ch.elca.visitors.service.service.VisitService;
import ch.elca.visitors.service.utils.IteratorUtil;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final AppointmentRepository appointmentRepository;
    private final ContactRepository contactRepository;
    private final VisitorRepository visitorRepository;

    private final VisitMapper visitMapper;

    public VisitDto addVisit(VisitDto visitDto) {


        Visit visit = visitMapper.mapToVisit(visitDto);


        if (Objects.nonNull(visitDto.getContact().getId())) {
            var contact = contactRepository.findById(visitDto.getContact().getId()).orElseThrow(() -> new ResourceNotFoundException("No contact found with id " +
                    visitDto.getContact().getId()));
            visit.setContact(contact);

        } else {
            visit.setContact(null);
        }


        if (Objects.nonNull(visitDto.getAppointment().getId())) {
            var appointment = appointmentRepository.findById(visitDto.getAppointment().getId()).orElseThrow(() -> new ResourceNotFoundException("No appointment found with id " +
                    visitDto.getAppointment().getId()));
            visit.setAppointment(appointment);
        } else {
            visit.setAppointment(null);
        }


        if (Objects.nonNull(visitDto.getVisitor())) {
            var visitor = visitorRepository.findById(visitDto.getVisitor().getId()).orElseThrow(() -> new ResourceNotFoundException("No visitor found with id " +
                    visitDto.getVisitor().getId()));
            visit.setVisitor(visitor);

        } else {
            visit.setVisitor(null);

        }

        var saved = visitRepository.save(visit);
        return visitMapper.mapToVisitDto(saved);
    }


    public Page<VisitDto> getAllVisits(PageRequest pageRequest) {
        var visits = visitRepository.findAll(pageRequest);
        return visits
                .map(visitMapper::mapToVisitDto);
    }


    public void checkoutVisit(Long visitId, String badgeNumber) {

        var visit = visitRepository.findById(visitId).orElseThrow(() -> new ResourceNotFoundException("No visit found with id " + visitId));

        // verify if badgeNumber valid before updating check out time
        if (visit.getBadgeNumber().equals(badgeNumber)) {
            visit.setCheckedOut(LocalDateTime.now());
            visitRepository.save(visit);
        } else {
            throw new ResourceNotFoundException("Badge number " + badgeNumber + " invalid");
        }
    }


    public List<VisitDto> searchActiveVisitsByVisitorNameOrEmail(String search) {
        var visits = IteratorUtil.toList(visitRepository.findAll(buildSearchActiveVisitsPredicate(search)));

        return visits.stream()
                .map(visitMapper::mapToVisitDto)
                .collect(Collectors.toList());
    }


    public Page<VisitDto> searchActiveVisitors(PageRequest pageRequest) {

        var visits = visitRepository.findAll(buildSearchActiveVisitorsPredicate(), pageRequest);
        return visits
                .map(visitMapper::mapToVisitDto);
    }

    public Page<VisitDto> searchPastVisitors(PageRequest pageRequest, LocalDate dateFrom) {
        var visits = visitRepository.findAll(buildSearchPastVisitorsPredicate(dateFrom), pageRequest);
        return visits
                .map(visitMapper::mapToVisitDto);
    }


    public Page<VisitDto> searchVisitsByVisitorName(PageRequest pageRequest, String search) {
        var visits = visitRepository.findAll(buildSearchVisitsByVisitorNamePredicate(search), pageRequest);
        return visits
                .map(visitMapper::mapToVisitDto);
    }


    public void exportListOfActiveVisitors(HttpServletResponse response) throws IOException {
        var activeVisits = IteratorUtil.toList(visitRepository.findAll(buildSearchActiveVisitorsPredicate()));

        var activeVisitsDto = activeVisits
                .stream()
                .map(visitMapper::mapToVisitDto)
                .sorted(Comparator.comparing(VisitDto::getCheckedIn))
                .collect(Collectors.toList());

        VisitExporter visitExporter = new VisitExporter(activeVisitsDto);
        visitExporter.export(response);
    }


    public void exportListOfPastVisitors(HttpServletResponse response, LocalDate dateFrom) throws IOException {
        var pastVisits = IteratorUtil.toList(visitRepository.findAll(buildSearchPastVisitorsPredicate(dateFrom)));

        var pastVisitsDto = pastVisits
                .stream()
                .map(visitMapper::mapToVisitDto)
                .sorted(Comparator.comparing(VisitDto::getCheckedIn))
                .collect(Collectors.toList());

        VisitExporter visitExporter = new VisitExporter(pastVisitsDto);
        visitExporter.export(response);
    }


    private BooleanBuilder buildSearchActiveVisitorsPredicate() {
        var qVisit = QVisit.visit;
        var predicate = new BooleanBuilder();

        LocalDateTime todayStart = LocalDate.now().atTime(0, 0, 0);
        LocalDateTime todayEnd = LocalDate.now().atTime(23, 59, 59);

        predicate
                .and(qVisit.checkedOut.isNull())
                .and(qVisit.checkedIn.between(todayStart, todayEnd));

        return predicate;
    }


    private BooleanBuilder buildSearchActiveVisitsPredicate(String search) {
        var qVisit = QVisit.visit;
        var predicate = new BooleanBuilder();

        if (Objects.nonNull(search)) {

            LocalDateTime todayStart = LocalDate.now().atTime(0, 0, 0);
            LocalDateTime todayEnd = LocalDate.now().atTime(23, 59, 59);

            predicate
                    .and(qVisit.visitor.lastName.containsIgnoreCase(search)
                            .or(qVisit.visitor.firstName.containsIgnoreCase(search).or(qVisit.visitor.email.containsIgnoreCase(search))))
                    .and(qVisit.checkedOut.isNull())
                    .and(qVisit.checkedIn.between(todayStart, todayEnd));
        }

        return predicate;
    }


    private BooleanBuilder buildSearchPastVisitorsPredicate(LocalDate dateFrom) {
        var qVisit = QVisit.visit;
        var predicate = new BooleanBuilder();

//        LocalDateTime dateFromWithTime = dateFrom.atTime(0,0,0);
        LocalDateTime dateFromWithTime = dateFrom.atStartOfDay();
        LocalDateTime dateTimeToday = LocalDate.now().atTime(23, 59, 59);

        predicate
                .and(qVisit.checkedOut.isNotNull())
                .and(qVisit.checkedIn.between(dateFromWithTime, dateTimeToday));

        return predicate;
    }


    private BooleanBuilder buildSearchVisitsByVisitorNamePredicate(String search) {
        var qVisit = QVisit.visit;
        var predicate = new BooleanBuilder();

        if (Objects.nonNull(search)) {

            predicate
                    .and(qVisit.visitor.lastName.containsIgnoreCase(search))
                    .or(qVisit.visitor.firstName.containsIgnoreCase(search));
        }

        return predicate;
    }

}
