package ch.elca.visitors.rest.controller;

import ch.elca.visitors.service.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/appointment")
@CrossOrigin()
public class AppointmentController {

//    private AppointmentService appointmentService;
}
