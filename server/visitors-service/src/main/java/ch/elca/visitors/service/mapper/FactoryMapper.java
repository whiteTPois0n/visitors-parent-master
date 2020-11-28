package ch.elca.visitors.service.mapper;

import ch.elca.visitors.persistence.entity.Factory;
import ch.elca.visitors.service.dto.FactoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FactoryMapper {

    FactoryDto mapToFactoryDto(Factory factory);

    Factory mapToFactory(FactoryDto factoryDto);

}
