package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.VisitorDto;

import java.util.List;

public interface VisitorService {

    VisitorDto addVisitor(VisitorDto visitorDto);

    List<VisitorDto> getAllVisitors();

    VisitorDto findVisitor(Long id);

    VisitorDto updateVisitor(VisitorDto visitorDto);

    void deleteVisitor(Long id);

    List<VisitorDto> findVisitorByLastName(String lastName);

//    Page<Visitor> getVisitorBy( String firstName,String lastName, Pageable pageable);

}
