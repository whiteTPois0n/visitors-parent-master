package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.repository.VisitorTypeRepository;
import ch.elca.visitors.service.dto.VisitorTypeDto;
import ch.elca.visitors.service.exception.ResourceNotFoundException;
import ch.elca.visitors.service.mapper.VisitorTypeMapper;
import ch.elca.visitors.service.service.VisitorTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitorTypeServiceImpl implements VisitorTypeService {


    private final VisitorTypeRepository visitorTypeRepository;
    private final VisitorTypeMapper visitorTypeMapper;


    public void addVisitorType(VisitorTypeDto visitorTypeDto) {
        var visitorType = visitorTypeMapper.toVisitorType(visitorTypeDto);
        visitorTypeRepository.save(visitorType);
    }


    public List<VisitorTypeDto> getAllVisitorTypes() {
        var visitorTypes = visitorTypeRepository.findAll();
        return visitorTypes
                .stream()
                .map(visitorTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public VisitorTypeDto findVisitorType(Long id) {
        var visitorType = visitorTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, factory with id " + id + " not found"));
        return visitorTypeMapper.toDto(visitorType);
    }

    public void updateVisitorType(VisitorTypeDto visitorTypeDto) {
        visitorTypeRepository.findById(visitorTypeDto.getId())
                .map(visitorType -> {
                    visitorType.setType(visitorTypeDto.getType());
                    return visitorTypeRepository.save(visitorType);
                }).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, factory with id " + visitorTypeDto.getId() + " not found"));
    }

}
