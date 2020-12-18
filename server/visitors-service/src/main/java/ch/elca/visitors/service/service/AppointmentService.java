package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.AppointmentDto;
import ch.elca.visitors.service.dto.VisitorDto;

import java.util.List;

public interface AppointmentService {

    AppointmentDto addAppointment(AppointmentDto appointmentDto);

    AppointmentDto findAppointment(Long id);

    List<AppointmentDto> searchAppointmentByVisitorFirstNameOrLastNameOrEmail(String search);

}
