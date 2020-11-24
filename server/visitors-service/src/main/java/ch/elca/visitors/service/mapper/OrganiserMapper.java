package ch.elca.visitors.service.mapper;

import ch.elca.visitors.persistence.entity.Organiser;
import ch.elca.visitors.service.dto.OrganiserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganiserMapper {

    OrganiserDto mapToDto(Organiser organiser);

    Organiser mapToEntity(OrganiserDto organiserDto);
}
