package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.VisitDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface VisitService {

    VisitDto addVisit(VisitDto visitDto);

    Page<VisitDto> getAllVisits(PageRequest pageRequest);

    void checkoutVisit(Long visitId);

    List<VisitDto> searchActiveVisitsByVisitorNameOrEmail(String search);

    Page<VisitDto> searchActiveVisitors(PageRequest pageRequest);

    Page<VisitDto> searchPastVisitors(PageRequest pageRequest, LocalDate dateFrom);

    Page<VisitDto> searchVisitsByVisitorName(PageRequest pageRequest, String search);

    void exportListOfActiveVisitors(HttpServletResponse response) throws IOException;

    void exportListOfPastVisitors(HttpServletResponse response, LocalDate dateFrom) throws IOException;

}