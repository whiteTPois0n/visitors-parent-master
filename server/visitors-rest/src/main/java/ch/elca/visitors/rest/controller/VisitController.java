package ch.elca.visitors.rest.controller;

import ch.elca.visitors.persistence.entity.QVisit;
import ch.elca.visitors.service.dto.VisitDto;
import ch.elca.visitors.service.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visit")
@CrossOrigin()
public class VisitController {


    private final VisitService visitService;


    @PostMapping("/create")
    public VisitDto addVisit(@RequestBody VisitDto visitDto) {
        return visitService.addVisit(visitDto);
    }


    @GetMapping("/search-all/page/{pageNumber}/{pageSize}")
    public Page<VisitDto> getAllVisits(@PathVariable("pageNumber") Integer pageNumber,
                                       @PathVariable("pageSize") Integer pageSize) {

        var qVisit = QVisit.visit;
        return visitService.getAllVisits(PageRequest.of(pageNumber, pageSize, Sort.by(
                Sort.Direction.ASC, qVisit.checkedIn.getMetadata().getName())));
    }


    @GetMapping("/search-active-visits")
    public List<VisitDto> searchActiveVisitsByVisitorNameOrEmail(@RequestParam(required = false) String search) {
        return visitService.searchActiveVisitsByVisitorNameOrEmail(search);
    }


    @GetMapping("/checkout")
    public void checkoutVisit(@RequestParam Long visitId,
                              @RequestParam String badgeNumber) {
        visitService.checkoutVisit(visitId, badgeNumber);
    }


    @GetMapping("/active-visitors/page/{pageNumber}/{pageSize}")
    public Page<VisitDto> searchActiveVisitors(@PathVariable("pageNumber") Integer pageNumber,
                                               @PathVariable("pageSize") Integer pageSize) {

        var qVisit = QVisit.visit;
        return visitService.searchActiveVisitors(PageRequest.of(pageNumber, pageSize, Sort.by(
                Sort.Direction.ASC, qVisit.checkedIn.getMetadata().getName())));
    }


    @GetMapping("/past-visitors/page/{pageNumber}/{pageSize}")
    public Page<VisitDto> searchPastVisitors(@RequestParam(required = false)
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                             @PathVariable("pageNumber") Integer pageNumber,
                                             @PathVariable("pageSize") Integer pageSize) {

        var qVisit = QVisit.visit;
        return visitService.searchPastVisitors(PageRequest.of(pageNumber, pageSize, Sort.by(
                Sort.Direction.ASC, qVisit.checkedIn.getMetadata().getName())), dateFrom);
    }

    @GetMapping("/search-visits/{pageNumber}/{pageSize}")
    public Page<VisitDto> searchVisitsByVisitorName(@RequestParam(required = false) String search,
                                                    @PathVariable("pageNumber") Integer pageNumber,
                                                    @PathVariable("pageSize") Integer pageSize) {

        var qVisit = QVisit.visit;
        return visitService.searchVisitsByVisitorName(PageRequest.of(pageNumber, pageSize, Sort.by(
                Sort.Direction.ASC, qVisit.checkedIn.getMetadata().getName())), search);
    }


    @GetMapping("/active-visitors/export")
    public void exportActiveVisitors(HttpServletResponse response) throws IOException {
        visitService.exportListOfActiveVisitors(response);
    }


    @GetMapping("/past-visitors/export")
    public void exportPastVisitors(HttpServletResponse response,
                                   @RequestParam(required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom) throws IOException {

        visitService.exportListOfPastVisitors(response, dateFrom);
    }


    @GetMapping("/current-day-visits")
    public long countNumberOfVisitsToday() {
        return visitService.countNumberOfVisitsToday();
    }


    @GetMapping("/past-day-visits")
    public long countNumberOfVisitsYesterday() {
        return visitService.countNumberOfVisitsYesterday();
    }


    @GetMapping("/past-month-visits")
    public long countNumberOfPastMonthVisits(@RequestParam
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate today) {
        return visitService.countNumberOfPastMonthVisits(today);
    }


    @GetMapping("/past-year-visits")
    public long countNumberOfVisitsThisYear() {
        return visitService.countNumberOfPastYearVisits();
    }
}

