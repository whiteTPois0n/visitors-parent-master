package ch.elca.visitors.service.mapper;


import ch.elca.visitors.persistence.entity.Appointment;
import ch.elca.visitors.service.dto.AppointmentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentDto mapToAppointmentDto(Appointment appointment);

    Appointment mapToAppointment(AppointmentDto appointmentDto);

}

