package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.repository.FactoryRepository;
import ch.elca.visitors.service.dto.FactoryDto;
import ch.elca.visitors.service.exception.ResourceNotFoundException;
import ch.elca.visitors.service.mapper.FactoryMapper;
import ch.elca.visitors.service.service.FactoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactoryServiceImpl implements FactoryService {

    private final FactoryRepository factoryRepository;
    private final FactoryMapper factoryMapper;

    @Override
    public void addFactory(FactoryDto factoryDto) {
        var factory = factoryMapper.mapToEntity(factoryDto);
        factoryRepository.save(factory);
    }

    @Override
    public List<FactoryDto> getAllFactories() {
        var factories = factoryRepository.findAll();
        return factories
                .stream()
                .map(factoryMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public FactoryDto findFactory(Long id) {
        var factory = factoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, factory with id " + id + " not found"));
        return factoryMapper.mapToDto(factory);
    }

    @Override
    public void updateFactory(FactoryDto factoryDto) {
        factoryRepository.findById(factoryDto.getId())
                .map(factory -> {
                    factory.setDepartment(factoryDto.getDepartment());
                    return factoryRepository.save(factory);
                }).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, factory with id " + factoryDto.getId() + " not found"));
    }
}
