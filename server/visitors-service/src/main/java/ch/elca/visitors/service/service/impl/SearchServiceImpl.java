//package ch.elca.visitors.service.service.impl;
//
//import ch.elca.visitors.persistence.entity.QVisit;
//import ch.elca.visitors.persistence.entity.QVisitor;
//import ch.elca.visitors.persistence.repository.AppointmentRepository;
//import ch.elca.visitors.persistence.repository.VisitRepository;
//import ch.elca.visitors.persistence.repository.VisitorRepository;
//import ch.elca.visitors.service.dto.AppointmentDto;
//import ch.elca.visitors.service.dto.VisitDto;
//import ch.elca.visitors.service.dto.VisitorDto;
//import ch.elca.visitors.service.mapper.AppointmentMapper;
//import ch.elca.visitors.service.mapper.VisitMapper;
//import ch.elca.visitors.service.mapper.VisitorMapper;
//import ch.elca.visitors.service.service.SearchService;
//import ch.elca.visitors.service.utils.IteratorUtil;
//import com.querydsl.core.BooleanBuilder;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class SearchServiceImpl implements SearchService {
//
//    private final VisitorRepository visitorRepository;
//    private final VisitRepository visitRepository;
//    private final AppointmentRepository appointmentRepository;
//
//    private final VisitorMapper visitorMapper;
//    private final VisitMapper visitMapper;
//    private final AppointmentMapper appointmentMapper;
//
//
//    public List<VisitorDto> searchVisitorsByNameOrEmail(String search) {
//        var visitors = IteratorUtil.toList(visitorRepository.findAll(buildSearchVisitorByNameOrEmailPredicate(search)));
//
//        return visitors.stream()
//                .map(visitorMapper::mapToVisitorDto)
//                .collect(Collectors.toList());
//    }
//
//
//    private BooleanBuilder buildSearchVisitorByNameOrEmailPredicate(String search) {
//        var qVisitor = QVisitor.visitor;
//        var predicate = new BooleanBuilder();
//
//        if (Objects.nonNull(search)) {
//            predicate.and(qVisitor.lastName.containsIgnoreCase(search)
//                    .or(qVisitor.firstName.containsIgnoreCase(search).or(qVisitor.email.containsIgnoreCase(search))));
//        }
//
//        return predicate;
//    }
//
//
//    public List<VisitDto> searchActiveVisitsByVisitorNameOrEmail(String search) {
//        var visits = IteratorUtil.toList(visitRepository.findAll(buildSearchActiveVisitsPredicate(search)));
//
//        return visits.stream()
//                .map(visitMapper::mapToVisitDto)
//                .collect(Collectors.toList());
//    }
//
//
//    private BooleanBuilder buildSearchActiveVisitsPredicate(String search) {
//        var qVisit = QVisit.visit;
//        var predicate = new BooleanBuilder();
//
//        if (Objects.nonNull(search)) {
//
//            LocalDateTime todayStart = LocalDate.now().atTime(0, 0, 0);
//            LocalDateTime todayEnd = LocalDate.now().atTime(23, 59, 59);
//
//            predicate
//                    .and(qVisit.visitor.lastName.containsIgnoreCase(search)
//                            .or(qVisit.visitor.firstName.containsIgnoreCase(search).or(qVisit.visitor.email.containsIgnoreCase(search))))
//                    .and(qVisit.checkedOut.isNull())
//                    .and(qVisit.checkedIn.between(todayStart, todayEnd));
//        }
//
//        return predicate;
//    }
//
//
//    public Page<VisitDto> searchActiveVisitors(PageRequest pageRequest) {
//
//        var visits = visitRepository.findAll(buildSearchActiveVisitorsPredicate(), pageRequest);
//        return visits
//                .map(visitMapper::mapToVisitDto);
//    }
//
//
//    private BooleanBuilder buildSearchActiveVisitorsPredicate() {
//        var qVisit = QVisit.visit;
//        var predicate = new BooleanBuilder();
//
//        LocalDateTime todayStart = LocalDate.now().atTime(0, 0, 0);
//        LocalDateTime todayEnd = LocalDate.now().atTime(23, 59, 59);
//
//        predicate
//                .and(qVisit.checkedOut.isNull())
//                .and(qVisit.checkedIn.between(todayStart, todayEnd));
//
//        return predicate;
//    }
//
//
//    public Page<VisitDto> searchPastVisitors(PageRequest pageRequest, LocalDate dateFrom) {
//        var visits = visitRepository.findAll(buildSearchPastVisitorsPredicate(), pageRequest);
//        return visits
//                .map(visitMapper::mapToVisitDto);
//    }
//
//
//    private BooleanBuilder buildSearchPastVisitorsPredicate() {
//        var qVisit = QVisit.visit;
//        var predicate = new BooleanBuilder();
//
//        LocalDateTime today = LocalDate.now().atStartOfDay();
//
//        predicate
//                .and(qVisit.checkedOut.isNotNull())
//                .and(qVisit.checkedIn.before(today));
//
//        return predicate;
//    }
//
//
//    public Page<AppointmentDto> searchFutureVisitors(PageRequest pageRequest, LocalDate dateTo) {
//        var appointments = appointmentRepository.findAll(buildSearchFutureVisitorsPredicate(dateTo), pageRequest);
//        return appointments
//                .map(appointmentMapper::mapToAppointmentDto);
//    }
//
//
//    private BooleanBuilder buildSearchFutureVisitorsPredicate(LocalDate dateTo) {
//        var qVisit = QVisit.visit;
//        var predicate = new BooleanBuilder();
//
//        LocalDateTime today = LocalDate.now().atTime(23, 59, 59);
//
//        predicate
//                .and(qVisit.checkedOut.isNotNull())
//                .and(qVisit.checkedIn.after(today));
//
//        return predicate;
//    }
//
//}
