package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.CountHoursDto;
import ch.elca.visitors.service.dto.VisitDto;
import com.google.zxing.WriterException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface VisitService {

    VisitDto addVisit(VisitDto visitDto) throws IOException, WriterException;

    Page<VisitDto> getAllVisits(PageRequest pageRequest);

    void checkoutVisit(Long visitId, String badgeNumber);

    VisitDto searchActiveVisitByBadgeNumber (String badgeNumber);

    List<VisitDto> searchActiveVisitsByVisitorNameOrEmail(String search);

    Page<VisitDto> searchActiveVisitors(PageRequest pageRequest);

    Page<VisitDto> searchPastVisitors(PageRequest pageRequest, LocalDate dateFrom);

    Page<VisitDto> searchVisitsByVisitorName(PageRequest pageRequest, String search);

    void exportListOfActiveVisitors(HttpServletResponse response) throws IOException;

    void exportListOfPastVisitors(HttpServletResponse response, LocalDate dateFrom) throws IOException;

    long countNumberOfVisitsToday();

    long countNumberOfVisitsYesterday();

    long countNumberOfPastMonthVisits();

    long countNumberOfPastYearVisits();

    List<Long> pastYearVisitorStatistics();

    List<Long> currentYearVisitorStatistics();

    List<CountHoursDto> candidatesPeekHourVisitsStatistics();

    List<CountHoursDto> customersPeekHourVisitsStatistics();

    List<CountHoursDto> partnersPeekHourVisitsStatistics();

    List<CountHoursDto> providersPeekHourVisitsStatistics();

    List<CountHoursDto> othersPeekHourVisitsStatistics();

}