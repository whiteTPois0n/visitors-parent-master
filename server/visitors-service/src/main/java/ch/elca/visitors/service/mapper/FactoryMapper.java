package ch.elca.visitors.service.mapper;

import ch.elca.visitors.persistence.entity.Factory;
import ch.elca.visitors.service.dto.FactoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FactoryMapper {

    FactoryDto mapToDto(Factory factory);

    Factory mapToEntity(FactoryDto factoryDto);
}
