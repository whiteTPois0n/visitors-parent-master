package ch.elca.visitors.rest.controller;

import ch.elca.visitors.service.dto.VisitDto;
import ch.elca.visitors.service.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/visit")
@CrossOrigin()
public class VisitController {

    private final VisitService visitService;

    @PostMapping("/create")
    public VisitDto addVisit(@Valid @RequestBody VisitDto visitDto) {
        return visitService.addVisit(visitDto);
    }
}

