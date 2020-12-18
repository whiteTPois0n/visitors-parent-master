package ch.elca.visitors.service.mapper;

import ch.elca.visitors.persistence.entity.Visit;
import ch.elca.visitors.service.dto.VisitDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VisitMapper {

    VisitDto mapToVisitDto(Visit visit);

    @Mapping(target = "checkedIn", ignore = true)
    Visit mapToVisit(VisitDto visitDto);

}
