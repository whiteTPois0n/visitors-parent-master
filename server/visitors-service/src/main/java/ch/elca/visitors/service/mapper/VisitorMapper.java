package ch.elca.visitors.service.mapper;

import ch.elca.visitors.persistence.entity.Visitor;
import ch.elca.visitors.service.dto.SearchDto;
import ch.elca.visitors.service.dto.VisitorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VisitorMapper {

    VisitorDto mapToDto(Visitor visitor);

    @Mapping(target = "checkedOut", ignore = true)
    Visitor mapToEntity(VisitorDto visitorDto);

    @Mapping(target = "dateTime", source = "checkedIn")
    SearchDto mapToSearchDto(Visitor visitor);

}
