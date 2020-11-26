package ch.elca.visitors.rest;

import ch.elca.visitors.service.dto.SearchDto;
import ch.elca.visitors.service.dto.VisitorDto;
import ch.elca.visitors.service.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/visitor")
//@CrossOrigin()
public class VisitorController {

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private final VisitorService visitorService;


    @PostMapping("/create")
    public VisitorDto addVisitor(@Valid @RequestBody VisitorDto visitorDto) {
        return visitorService.addVisitor(visitorDto);
    }


    @GetMapping("search/all")
    public List<VisitorDto> getAllVisitors() {
        return visitorService.getAllVisitors();
    }


    @GetMapping("/{id}")
    public VisitorDto findVisitor(@PathVariable("id") Long id) {
        return visitorService.findVisitor(id);
    }


    @PutMapping("/update")
    public VisitorDto updateVisitor(@RequestBody VisitorDto visitorDto) {
        return visitorService.updateVisitor(visitorDto);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteVisitorById(@PathVariable Long id) {
        visitorService.deleteVisitor(id);
    }


    @GetMapping("/search-by-full-name")
    public List<SearchDto> searchByLastNameFirstName(@RequestParam(required = false) String lastName, @RequestParam(required = false) String firstName) {
        return visitorService.filterByLastNameAndFirstName(lastName, firstName);
    }

//    @GetMapping("/search-by-date")
//    public List<SearchDto> searchByDateFromDateTo(@RequestParam(required = false)
//                                                  @DateTimeFormat(pattern = DATE_PATTERN) LocalDate dateFrom,
//                                                  @RequestParam(required = false)
//                                                  @DateTimeFormat(pattern = DATE_PATTERN) LocalDate dateTo) {
//
//        return visitorService.filterByDateFromAndDateTo(dateFrom, dateTo);
//    }

    @GetMapping("/search-by-active-visitors")
    public List<SearchDto> searchByActiveVisitors(@RequestParam(required = false)
                                                  @DateTimeFormat(pattern = DATE_PATTERN) LocalDate dateFrom,
                                                  @RequestParam(required = false)
                                                  @DateTimeFormat(pattern = DATE_PATTERN) LocalDate dateTo) {

        return visitorService.generateListOfCurrentVisitors(dateFrom, dateTo);
    }

    @GetMapping("/search-by-future-visitors")
    public List<SearchDto> searchByFutureVisitors(@RequestParam(required = false)
                                                  @DateTimeFormat(pattern = DATE_PATTERN) LocalDate dateTo) {

        return visitorService.generateListOfFutureVisitors(dateTo);
    }

    @GetMapping("/search-by-past-visitors")
    public List<SearchDto> searchByPastVisitors(@RequestParam(required = false)
                                                @DateTimeFormat(pattern = DATE_PATTERN) LocalDate dateFrom) {

        return visitorService.generateListOfPastVisitors(dateFrom);
    }

}
