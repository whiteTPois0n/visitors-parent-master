package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.AppointmentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    AppointmentDto addAppointment(AppointmentDto appointmentDto);

    AppointmentDto findAppointment(Long id);

    void deleteAppointment (Long id);

    Page<AppointmentDto> getAllAppointments(PageRequest pageRequest);

    List<AppointmentDto> searchAppointmentsByVisitorNameOrEmail(String search);

    Page<AppointmentDto> searchFutureVisitors(PageRequest pageRequest, LocalDate dateTo);

    Page<AppointmentDto> searchAppointmentsByVisitorName(PageRequest pageRequest, String search);

    void exportListOfFutureVisitors(HttpServletResponse response, LocalDate dateTo) throws IOException;

}