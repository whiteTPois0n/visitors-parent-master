package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

public interface SearchService {

    List<SearchDto> filterByLastNameAndFirstName(String lastName, String firstNAme);

    Page<SearchDto> generateListOfActiveVisitors(PageRequest pageRequest, LocalDate dateFrom, LocalDate dateTo);

    Page<SearchDto> generateListOfFutureVisitors(PageRequest pageRequest, LocalDate dateTo);

    Page<SearchDto> generateListOfPastVisitors(PageRequest pageRequest, LocalDate dateFrom);

}
