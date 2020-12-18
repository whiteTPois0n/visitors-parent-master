package ch.elca.visitors.service.mapper;

import ch.elca.visitors.persistence.entity.Visitor;
import ch.elca.visitors.service.dto.VisitorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitorMapper {

    VisitorDto mapToVisitorDto(Visitor visitor);

    Visitor mapToVisitor(VisitorDto visitorDto);
}
