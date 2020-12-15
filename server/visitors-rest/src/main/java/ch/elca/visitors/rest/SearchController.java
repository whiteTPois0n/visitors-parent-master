package ch.elca.visitors.rest;

import ch.elca.visitors.persistence.entity.QOrganiser;
import ch.elca.visitors.persistence.entity.QVisitor;
import ch.elca.visitors.persistence.entity.SearchResultItem;
import ch.elca.visitors.service.dto.OrganiserDto;
import ch.elca.visitors.service.dto.SearchDto;
import ch.elca.visitors.service.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
@CrossOrigin()
public class SearchController {


    private final SearchService searchService;


    @GetMapping("/full-name")
    public List<SearchDto> searchByLastNameFirstName(@RequestParam(required = false) String lastName,
                                                     @RequestParam(required = false) String firstName) {
        return searchService.filterByLastNameAndFirstName(lastName, firstName);
    }

    @GetMapping("/organisers/full-name")
    public List<OrganiserDto> searchOrganisersByLastNameFirstName(@RequestParam(required = false) String lastName, @RequestParam(required = false) String firstName) {
        return searchService.filterOrganisersByLastNameAndFirstName(lastName, firstName);
    }


    @GetMapping("/active-visitors/page/{pageNumber}/{pageSize}")
    public Page<SearchDto> searchByActiveVisitors(@RequestParam(required = false)
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                  @RequestParam(required = false)
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                                  @PathVariable("pageNumber") Integer pageNumber,
                                                  @PathVariable("pageSize") Integer pageSize) {

        var visitor = QVisitor.visitor;
        return searchService.generateListOfActiveVisitors(PageRequest.of(pageNumber, pageSize, Sort.by(
                Sort.Direction.ASC, visitor.checkedIn.getMetadata().getName())), dateFrom, dateTo);
    }


    @GetMapping("/future-visitors/page/{pageNumber}/{pageSize}")
    public Page<SearchDto> searchByFutureVisitors(@RequestParam(required = false)
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                                  @PathVariable("pageNumber") Integer pageNumber,
                                                  @PathVariable("pageSize") Integer pageSize) {

        var organiser = QOrganiser.organiser;
        return searchService.generateListOfFutureVisitors(PageRequest.of(pageNumber, pageSize, Sort.by(
                Sort.Direction.ASC, organiser.dateTime.getMetadata().getName())), dateTo);
    }


    @GetMapping("/past-visitors/page/{pageNumber}/{pageSize}")
    public Page<SearchDto> searchByPastVisitors(@RequestParam(required = false)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                @PathVariable("pageNumber") Integer pageNumber,
                                                @PathVariable("pageSize") Integer pageSize) {

        var visitor = QVisitor.visitor;
        return searchService.generateListOfPastVisitors(PageRequest.of(pageNumber, pageSize, Sort.by(
                Sort.Direction.ASC, visitor.checkedIn.getMetadata().getName())), dateFrom);
    }


    @GetMapping("/active-visitors/export")
    public void exportActiveVisitors(HttpServletResponse response,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) throws IOException {

        searchService.exportListOfActiveVisitors(response, dateFrom, dateTo);
    }


    @GetMapping("/past-visitors/export")
    public void exportPastVisitors(HttpServletResponse response,
                                   @RequestParam(required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom) throws IOException {

        searchService.exportListOfPastVisitors(response, dateFrom);
    }


    @GetMapping("/future-visitors/export")
    public void exportFutureVisitors(HttpServletResponse response,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) throws IOException {

        searchService.exportListOfFutureVisitors(response, dateTo);
    }

    @GetMapping("/search-visitors-organisers/{pageNumber}/{pageSize}")
    public Page<SearchResultItem> searchVisitorsOrganisers(@RequestParam(required = false) String lastName,
                                                           @RequestParam(required = false) String firstName,
                                                           @PathVariable("pageNumber") Integer pageNumber,
                                                           @PathVariable("pageSize") Integer pageSize) {
        return searchService.searchVisitorsOrganisers(lastName, firstName, PageRequest.of(pageNumber, pageSize));
    }
}