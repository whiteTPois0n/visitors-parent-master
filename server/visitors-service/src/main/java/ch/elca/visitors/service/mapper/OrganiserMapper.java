package ch.elca.visitors.service.mapper;

import ch.elca.visitors.persistence.entity.Organiser;
import ch.elca.visitors.persistence.entity.Visitor;
import ch.elca.visitors.service.dto.OrganiserDto;
import ch.elca.visitors.service.dto.SearchDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrganiserMapper {

    OrganiserDto mapToDto(Organiser organiser);

    Organiser mapToEntity(OrganiserDto organiserDto);

    SearchDto mapToSearchDto(Organiser organiser);
}
