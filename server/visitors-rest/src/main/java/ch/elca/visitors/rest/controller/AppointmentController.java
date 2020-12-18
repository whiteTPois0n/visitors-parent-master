package ch.elca.visitors.rest.controller;

import ch.elca.visitors.service.dto.AppointmentDto;
import ch.elca.visitors.service.dto.VisitorDto;
import ch.elca.visitors.service.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/appointment")
@CrossOrigin()
public class AppointmentController {


    private final AppointmentService appointmentService;


    @PostMapping("/create")
    public AppointmentDto addAppointment(@Valid @RequestBody AppointmentDto appointmentDto) {
        return appointmentService.addAppointment(appointmentDto);
    }


    @GetMapping("/{id}")
    public AppointmentDto findAppointment(@PathVariable("id") Long id) {
        return appointmentService.findAppointment(id);
    }


    @GetMapping("/search-appointments")
    public List<AppointmentDto> searchAppointmentByVisitorDetails(@RequestParam(required = false) String search) {
        return appointmentService.searchAppointmentByVisitorFirstNameOrLastNameOrEmail(search);
    }

}

