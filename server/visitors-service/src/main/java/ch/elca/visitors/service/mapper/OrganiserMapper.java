package ch.elca.visitors.service.mapper;

import ch.elca.visitors.persistence.entity.Organiser;
import ch.elca.visitors.service.dto.OrganiserDto;
import ch.elca.visitors.service.dto.SearchDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganiserMapper {

    OrganiserDto mapToOrganiserDto(Organiser organiser);

    Organiser mapToOrganiser(OrganiserDto organiserDto);

    SearchDto mapToSearchDto(Organiser organiser);

}
