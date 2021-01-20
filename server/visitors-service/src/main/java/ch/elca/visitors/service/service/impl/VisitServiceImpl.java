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
import ch.elca.visitors.service.service.QrCodeGenService;
import ch.elca.visitors.service.service.TwilioSmsService;
import ch.elca.visitors.service.service.VisitService;
import ch.elca.visitors.service.utils.IteratorUtil;
import com.google.zxing.WriterException;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private static final String QR_CODE_IMAGE_PATH = "C://Users//AAH//Desktop//QR_code_gen//QR_sample.png//";
    private static final Integer QR_CODE_IMAGE_WIDTH = 120;
    private static final Integer QR_CODE_IMAGE_HEIGHT = 120;
    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsServiceImpl.class);
    private final VisitRepository visitRepository;
    private final AppointmentRepository appointmentRepository;
    private final ContactRepository contactRepository;
    private final VisitorRepository visitorRepository;
    private final TwilioSmsService twilioService;
    private final QrCodeGenService qrCodeGenService;
    private final VisitMapper visitMapper;
//    private final String QR_CODE_IMAGE_PATH = String.valueOf(getClass().getResourceAsStream("/generated"));

    public VisitDto addVisit(VisitDto visitDto) throws IOException, WriterException {


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

//        twilio api send message to host if present
//        if (Objects.nonNull(visitDto.getContact().getId())) {
//            contactRepository.findById(visitDto.getContact().getId()).ifPresent(contact -> {
//                        String message = "Your visitor " + visitDto.getVisitor().getLastName() + " " + visitDto.getVisitor().getFirstName() + " has arrived and is waiting for you in the lobby";
//                        twilioService.sendSms(contact.getPhoneNumber(), message);
//                    }
//            );
//        }

        var saved = visitRepository.save(visit);
//        var data = saved.getId() + " " + saved.getBadgeNumber();
        var data = saved.getBadgeNumber();

        qrCodeGenService.generateQrCode(data, QR_CODE_IMAGE_PATH, QR_CODE_IMAGE_WIDTH, QR_CODE_IMAGE_HEIGHT);
        LOGGER.info("Qr Code generated with badgeId: " + saved.getBadgeNumber());
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


    public VisitDto searchActiveVisitByBadgeNumber(String badgeNumber) {
        var visit = visitRepository.findOne(buildSearchActiveVisitByBadgeNumberPredicate(badgeNumber)).orElseThrow(() -> new ResourceNotFoundException("Oops an error occurred while checking out!"));
        return visitMapper.mapToVisitDto(visit);

    }


    public List<VisitDto> searchActiveVisitsByVisitorNameOrEmail(String search) {
        return StreamSupport
                .stream(visitRepository.findAll(buildSearchActiveVisitsPredicate(search)).spliterator(), false)
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


    @Override
    public List<Long> pastYearVisitorStatistics() {

        var pastYear = LocalDate.now().getYear() - 1;

        var janVisitors = visitRepository.count(buildMonthWith31DaysPredicate(pastYear, 1));
        var febVisitors = visitRepository.count(buildFebDaysPredicate(pastYear));
        var marVisitors = visitRepository.count(buildMonthWith31DaysPredicate(pastYear, 3));
        var aprVisitors = visitRepository.count(buildMonthWith30DaysPredicate(pastYear, 4));
        var mayVisitors = visitRepository.count(buildMonthWith31DaysPredicate(pastYear, 5));
        var junVisitors = visitRepository.count(buildMonthWith30DaysPredicate(pastYear, 6));
        var julVisitors = visitRepository.count(buildMonthWith31DaysPredicate(pastYear, 7));
        var augVisitors = visitRepository.count(buildMonthWith31DaysPredicate(pastYear, 8));
        var sepVisitors = visitRepository.count(buildMonthWith30DaysPredicate(pastYear, 9));
        var octVisitors = visitRepository.count(buildMonthWith31DaysPredicate(pastYear, 10));
        var novVisitors = visitRepository.count(buildMonthWith30DaysPredicate(pastYear, 11));
        var decVisitors = visitRepository.count(buildMonthWith31DaysPredicate(pastYear, 12));

        return Stream.of(janVisitors, febVisitors, marVisitors, aprVisitors, mayVisitors, junVisitors, julVisitors, augVisitors,
                sepVisitors, octVisitors, novVisitors, decVisitors)
                .collect(Collectors.toList());
    }


    @Override
    public List<Long> currentYearVisitorStatistics() {
        var currentYear = LocalDate.now().getYear();

        var janVisitors = visitRepository.count(buildMonthWith31DaysPredicate(currentYear, 1));
        var febVisitors = visitRepository.count(buildFebDaysPredicate(currentYear));
        var marVisitors = visitRepository.count(buildMonthWith31DaysPredicate(currentYear, 3));
        var aprVisitors = visitRepository.count(buildMonthWith30DaysPredicate(currentYear, 4));
        var mayVisitors = visitRepository.count(buildMonthWith31DaysPredicate(currentYear, 5));
        var junVisitors = visitRepository.count(buildMonthWith30DaysPredicate(currentYear, 6));
        var julVisitors = visitRepository.count(buildMonthWith31DaysPredicate(currentYear, 7));
        var augVisitors = visitRepository.count(buildMonthWith31DaysPredicate(currentYear, 8));
        var sepVisitors = visitRepository.count(buildMonthWith30DaysPredicate(currentYear, 9));
        var octVisitors = visitRepository.count(buildMonthWith31DaysPredicate(currentYear, 10));
        var novVisitors = visitRepository.count(buildMonthWith30DaysPredicate(currentYear, 11));
        var decVisitors = visitRepository.count(buildMonthWith31DaysPredicate(currentYear, 12));

        return Stream.of(janVisitors, febVisitors, marVisitors, aprVisitors, mayVisitors, junVisitors, julVisitors, augVisitors,
                sepVisitors, octVisitors, novVisitors, decVisitors)
                .collect(Collectors.toList());
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


    private BooleanBuilder buildSearchActiveVisitByBadgeNumberPredicate(String badgeNumber) {
        var qVisit = QVisit.visit;
        var predicate = new BooleanBuilder();

        if (Objects.nonNull(badgeNumber)) {

            LocalDateTime todayStart = LocalDate.now().atTime(0, 0, 0);
            LocalDateTime todayEnd = LocalDate.now().atTime(23, 59, 59);

            predicate
                    .and(qVisit.badgeNumber.eq(badgeNumber)
                            .and(qVisit.checkedIn.between(todayStart, todayEnd)
                                    .and(qVisit.checkedOut.isNull())));
        }

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

        var qVisit = QVisit.visit;
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

        if (today.getMonth().getValue() == 1) {
            var pastYear = year - 1;
            pastMonthDateBegin = LocalDate.of(pastYear, pastMonth, 1).atStartOfDay();
            pastMonthDateEnd = LocalDate.of(pastYear, pastMonth, lastDayOfPreviousMonth).atTime(23, 59, 59);
        }

        return predicate
                .and(qVisit.checkedOut.between(pastMonthDateBegin, pastMonthDateEnd));
    }


    private BooleanBuilder buildCountNumberOfPastYearVisitsPredicate() {
        var qVisit = QVisit.visit;
        var predicate = new BooleanBuilder();

        var pastYear = LocalDate.now().getYear() - 1;
        var pastYearStart = LocalDate.of(pastYear, 1, 1).atStartOfDay();
        var pastYearYearEnd = LocalDate.of(pastYear, 12, 31).atTime(23, 59, 59);

        return predicate
                .and(qVisit.checkedIn.between(pastYearStart, pastYearYearEnd));
    }


    private BooleanBuilder buildMonthWith31DaysPredicate(int year, int month) {
        var qVisit = QVisit.visit;
        var predicate = new BooleanBuilder();

        var monthStart = LocalDate.of(year, month, 1).atStartOfDay();
        var monthEnd = LocalDate.of(year, month, 31).atTime(23, 59, 59);

        return predicate
                .and(qVisit.checkedIn.between(monthStart, monthEnd));

    }


    private BooleanBuilder buildFebDaysPredicate(int year) {
        var qVisit = QVisit.visit;
        var predicate = new BooleanBuilder();

        var monthStart = LocalDate.of(year, 2, 1).atStartOfDay();
        var lengthOfMonth = monthStart.toLocalDate().lengthOfMonth();

        var monthEnd = LocalDate.of(year, 2, lengthOfMonth).atTime(23, 59, 59);

        return predicate
                .and(qVisit.checkedIn.between(monthStart, monthEnd));
    }


    private BooleanBuilder buildMonthWith30DaysPredicate(int year, int month) {
        var qVisit = QVisit.visit;
        var predicate = new BooleanBuilder();

        var monthStart = LocalDate.of(year, month, 1).atStartOfDay();
        var monthEnd = LocalDate.of(year, month, 30).atTime(23, 59, 59);

        return predicate
                .and(qVisit.checkedIn.between(monthStart, monthEnd));
    }
}