package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.SearchDto;

import java.time.LocalDate;
import java.util.List;

public interface SearchService {

    List<SearchDto> filterByLastNameAndFirstName(String lastName, String firstNAme);

    List<SearchDto> generateListOfCurrentVisitors(LocalDate dateFrom, LocalDate dateTo);

    List<SearchDto> generateListOfFutureVisitors(LocalDate dateTo);

    List<SearchDto> generateListOfPastVisitors(LocalDate dateFrom);
}
