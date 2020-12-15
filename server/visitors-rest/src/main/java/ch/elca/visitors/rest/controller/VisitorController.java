package ch.elca.visitors.rest.controller;

import ch.elca.visitors.service.dto.VisitorDto;
import ch.elca.visitors.service.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import java.util.List;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/visitor")
@CrossOrigin()
public class VisitorController {

    private final VisitorService visitorService;


    @PostMapping("/create")
    public VisitorDto addVisitor(@Valid @RequestBody VisitorDto visitorDto) {
        return visitorService.addVisitor(visitorDto);
    }


    @GetMapping("/search/all")
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

    @GetMapping("/search-visitor")
    public List<VisitorDto> searchVisitorByNameOrEmail(@RequestParam(required = false) String firstName,
                                                       @RequestParam(required = false) String lastName,
                                                       @RequestParam(required = false) String email) {
        return visitorService.searchVisitorByNameOrEmail(firstName, lastName, email);
    }

}
