package ch.elca.visitors.service.mapper;

import ch.elca.visitors.persistence.entity.VisitorType;
import ch.elca.visitors.service.dto.VisitorTypeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitorTypeMapper {

    VisitorTypeDto mapToVisitorTypeDto(VisitorType visitorType);

    VisitorType mapToVisitorType(VisitorTypeDto visitorTypeDto);

}
