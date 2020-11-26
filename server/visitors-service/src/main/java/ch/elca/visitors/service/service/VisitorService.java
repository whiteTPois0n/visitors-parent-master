package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.SearchDto;
import ch.elca.visitors.service.dto.VisitorDto;

import java.time.LocalDate;
import java.util.List;

public interface VisitorService {

    VisitorDto addVisitor(VisitorDto visitorDto);

    List<VisitorDto> getAllVisitors();

    VisitorDto findVisitor(Long id);

    VisitorDto updateVisitor(VisitorDto visitorDto);

    void deleteVisitor(Long id);

    List<SearchDto> filterByLastNameAndFirstName(String lastName, String firstNAme);

//    List<SearchDto> filterByDateFromAndDateTo(LocalDate dateFrom, LocalDate dateTo);

    List<SearchDto> generateListOfCurrentVisitors(LocalDate dateFrom, LocalDate dateTo);

    List<SearchDto> generateListOfFutureVisitors(LocalDate dateTo);

    List<SearchDto> generateListOfPastVisitors(LocalDate dateFrom);

}
