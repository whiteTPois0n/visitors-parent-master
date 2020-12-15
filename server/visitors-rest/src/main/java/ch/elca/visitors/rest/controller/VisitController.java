package ch.elca.visitors.rest.controller;

import ch.elca.visitors.service.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/visit")
@CrossOrigin()
public class VisitController {

//    private final VisitService visitService;
}
