package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.VisitorTypeDto;

import java.util.List;

public interface VisitorTypeService {

    void addVisitorType(VisitorTypeDto visitorTypeDto);

    List<VisitorTypeDto> getAllVisitorTypes();

    VisitorTypeDto findVisitorType(Long id);

    void updateVisitorType(VisitorTypeDto visitorTypeDto);

//    void deleteVisitorType(Long id);

}
