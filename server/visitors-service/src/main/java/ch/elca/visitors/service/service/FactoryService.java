package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.FactoryDto;
import ch.elca.visitors.service.dto.VisitorTypeDto;

import java.util.List;

public interface FactoryService {

    void addFactory(FactoryDto factoryDto);

    List<FactoryDto> getAllFactories();

    FactoryDto findFactory(Long id);

    void updateFactory(FactoryDto factoryDto);
}
