package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.SearchDto;
import ch.elca.visitors.service.dto.VisitorDto;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitorService {

    VisitorDto addVisitor(VisitorDto visitorDto);

    List<VisitorDto> getAllVisitors();

    VisitorDto findVisitor(Long id);

    VisitorDto updateVisitor(VisitorDto visitorDto);

    void deleteVisitor(Long id);

    List<SearchDto> findVisitorByLastNameAndFirstName(String lastName, String firstNAme);

}
