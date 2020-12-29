package ch.elca.visitors.rest.controller;

import ch.elca.visitors.persistence.entity.QAppointment;
import ch.elca.visitors.service.dto.AppointmentDto;
import ch.elca.visitors.service.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
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


    @GetMapping("/search-all/page/{pageNumber}/{pageSize}")
    public Page<AppointmentDto> getAllVisits(@PathVariable("pageNumber") Integer pageNumber,
                                             @PathVariable("pageSize") Integer pageSize) {

        var qAppointment = QAppointment.appointment;
        return appointmentService.getAllAppointments(PageRequest.of(pageNumber, pageSize, Sort.by(
                Sort.Direction.ASC, qAppointment.appointmentDate.getMetadata().getName())));
    }


    @GetMapping("/search-appointments")
    public List<AppointmentDto> searchAppointmentsByVisitorNameOrEmail(@RequestParam(required = false) String search) {
        return appointmentService.searchAppointmentsByVisitorNameOrEmail(search);
    }


    @GetMapping("/future-visitors/page/{pageNumber}/{pageSize}")
    public Page<AppointmentDto> searchFutureVisitors(@RequestParam(required = false)
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                                     @PathVariable("pageNumber") Integer pageNumber,
                                                     @PathVariable("pageSize") Integer pageSize) {

        var qAppointment = QAppointment.appointment;
        return appointmentService.searchFutureVisitors(PageRequest.of(pageNumber, pageSize, Sort.by(
                Sort.Direction.ASC, qAppointment.appointmentDate.getMetadata().getName())), dateTo);
    }


    @GetMapping("/search-appointments/{pageNumber}/{pageSize}")
    public Page<AppointmentDto> searchAppointmentsByVisitorName(@RequestParam(required = false) String search,
                                                                @PathVariable("pageNumber") Integer pageNumber,
                                                                @PathVariable("pageSize") Integer pageSize) {

        var qAppointment = QAppointment.appointment;
        return appointmentService.searchAppointmentsByVisitorName(PageRequest.of(pageNumber, pageSize, Sort.by(
                Sort.Direction.ASC, qAppointment.appointmentDate.getMetadata().getName())), search);
    }

    @GetMapping("/future-visitors/export")
    public void exportFutureVisitors(HttpServletResponse response,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) throws IOException {

        appointmentService.exportListOfFutureVisitors(response, dateTo);
    }

}

