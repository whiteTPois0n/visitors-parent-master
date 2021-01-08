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
import com.querydsl.core.types.Predicate;
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

    @Override
    public long countNumberOfVisitsToday() {
        return visitRepository.count(buildCountNumberOfVisitsTodayPredicate());
    }

    @Override
    public long countNumberOfVisitsYesterday() {
        return visitRepository.count(buildCountNumberOfVisitsYesterdayPredicate());
    }


    @Override
    public long countNumberOfPastMonthVisits(LocalDate today) {
        return visitRepository.count(buildCountNumberOfPastMonthVisitsPredicate(today));
    }

    @Override
    public long countNumberOfPastYearVisits() {
        return visitRepository.count(buildCountNumberOfPastYearVisitsPredicate());
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


    private BooleanBuilder buildCountNumberOfVisitsTodayPredicate() {

        var qVisit = QVisit.visit;
        var predicate = new BooleanBuilder();

        LocalDateTime dateTimeStartToday = LocalDate.now().atStartOfDay();
        LocalDateTime dateTimeEndToday = LocalDate.now().atTime(23, 59, 59);

        return predicate
//                .and(qVisit.checkedOut.isNull().or(qVisit.checkedIn.isNotNull()))
                .and(qVisit.checkedIn.between(dateTimeStartToday, dateTimeEndToday));
    }


    private BooleanBuilder buildCountNumberOfVisitsYesterdayPredicate() {

        var qVisit= QVisit.visit;
        var predicate = new BooleanBuilder();

        LocalDateTime dateTimeStartYesterday = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime dateTimeEndYesterday = LocalDate.now().minusDays(1).atTime(23, 59, 59);

        return predicate
                .and(qVisit.checkedIn.between(dateTimeStartYesterday, dateTimeEndYesterday));
    }


    private BooleanBuilder buildCountNumberOfPastMonthVisitsPredicate(LocalDate today) {

        var qVisit = QVisit.visit;
        var predicate = new BooleanBuilder();
        var month = today.getMonth();
        var year = today.getYear();
        var pastMonth = month.minus(1);

        var pastMonthDateBegin = LocalDate.of(today.getYear(), pastMonth, 1).atStartOfDay();
        var lastDayOfPreviousMonth = pastMonthDateBegin.toLocalDate().lengthOfMonth();
        var pastMonthDateEnd = LocalDate.of(today.getYear(), pastMonth, lastDayOfPreviousMonth).atTime(23, 59, 59);

        if (today.getMonth().getValue() == 1){
            var pastYear = year-1;
            pastMonthDateBegin = LocalDate.of(pastYear, pastMonth, 1).atStartOfDay();
            pastMonthDateEnd = LocalDate.of(pastYear, pastMonth, lastDayOfPreviousMonth).atTime(23, 59, 59);
        }

        return predicate
                .and(qVisit.checkedOut.between(pastMonthDateBegin, pastMonthDateEnd));
    }


    private BooleanBuilder buildCountNumberOfPastYearVisitsPredicate() {
        var qVisit= QVisit.visit;
        var predicate = new BooleanBuilder();

        var pastYear = LocalDate.now().getYear()-1;
        var pastYearStart = LocalDate.of(pastYear, 1, 1).atStartOfDay();
        var pastYearYearEnd = LocalDate.of(pastYear, 12, 31).atTime(23,59,59);

        return predicate
                .and(qVisit.checkedIn.between(pastYearStart, pastYearYearEnd));
    }

}
