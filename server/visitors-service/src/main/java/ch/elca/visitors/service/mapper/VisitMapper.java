package ch.elca.visitors.service.mapper;

import ch.elca.visitors.persistence.entity.Visit;
import ch.elca.visitors.service.dto.VisitDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VisitMapper {

//    @Mapping(target = "contactId", source = "contact.id")
//    @Mapping(target = "appointmentId", source = "appointment.id")
    VisitDto mapToVisitDto(Visit visit);

//    @Mapping(target = "contact", ignore = true)
//    @Mapping(target = "appointment", ignore = true)
//    @Mapping(target = "contact.id", source = "contactId")
//    @Mapping(target = "appointment.id", source = "appointmentId")
    Visit mapToVisit(VisitDto visitDto);

}
