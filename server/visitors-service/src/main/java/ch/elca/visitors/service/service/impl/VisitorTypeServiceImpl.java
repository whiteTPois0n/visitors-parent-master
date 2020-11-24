package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.repository.VisitorTypeRepository;
import ch.elca.visitors.service.dto.VisitorTypeDto;
import ch.elca.visitors.service.mapper.VisitorTypeMapper;
import ch.elca.visitors.service.service.VisitorTypeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitorTypeServiceImpl implements VisitorTypeService {


    private final VisitorTypeRepository visitorTypeRepository;
    private final VisitorTypeMapper visitorTypeMapper;


    public String addVisitorType(VisitorTypeDto visitorTypeDto) {
        var visitorType = visitorTypeMapper.toVisitorType(visitorTypeDto);
        visitorTypeRepository.save(visitorType);
        return "Visitor Type successfully added";
    }
}
