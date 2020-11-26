package ch.elca.visitors.rest;

import ch.elca.visitors.service.dto.SearchDto;
import ch.elca.visitors.service.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
//@CrossOrigin()
public class SearchController {

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private final SearchService searchService;


    @GetMapping("/full-name")
    public List<SearchDto> searchByLastNameFirstName(@RequestParam(required = false) String lastName, @RequestParam(required = false) String firstName) {
        return searchService.filterByLastNameAndFirstName(lastName, firstName);
    }


    @GetMapping("/active-visitors")
    public List<SearchDto> searchByActiveVisitors(@RequestParam(required = false)
                                                  @DateTimeFormat(pattern = DATE_PATTERN) LocalDate dateFrom,
                                                  @RequestParam(required = false)
                                                  @DateTimeFormat(pattern = DATE_PATTERN) LocalDate dateTo) {

        return searchService.generateListOfCurrentVisitors(dateFrom, dateTo);
    }


    @GetMapping("/future-visitors")
    public List<SearchDto> searchByFutureVisitors(@RequestParam(required = false)
                                                  @DateTimeFormat(pattern = DATE_PATTERN) LocalDate dateTo) {
        return searchService.generateListOfFutureVisitors(dateTo);
    }


    @GetMapping("/past-visitors")
    public List<SearchDto> searchByPastVisitors(@RequestParam(required = false)
                                                @DateTimeFormat(pattern = DATE_PATTERN) LocalDate dateFrom) {

        return searchService.generateListOfPastVisitors(dateFrom);
    }

}