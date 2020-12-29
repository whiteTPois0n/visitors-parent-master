package ch.elca.visitors.service.mapper;

import ch.elca.visitors.persistence.entity.Appointment;
import ch.elca.visitors.persistence.entity.Contact;
import ch.elca.visitors.service.dto.AppointmentDto;
import ch.elca.visitors.service.dto.ContactDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    ContactDto mapToContactDto(Contact contact);

    Contact mapToContact(ContactDto contactDto);

}
