package ch.elca.visitors.service.service;

import ch.elca.visitors.persistence.entity.SearchResultItem;
import ch.elca.visitors.service.dto.OrganiserDto;
import ch.elca.visitors.service.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface SearchService {

    //    missing pagination
    List<SearchDto> filterByLastNameAndFirstName(String lastName, String firstName);

    List<OrganiserDto> filterOrganisersByLastNameAndFirstName(String lastName, String firstName);

    Page<SearchDto> generateListOfActiveVisitors(PageRequest pageRequest, LocalDate dateFrom, LocalDate dateTo);

    Page<SearchDto> generateListOfFutureVisitors(PageRequest pageRequest, LocalDate dateTo);

    Page<SearchDto> generateListOfPastVisitors(PageRequest pageRequest, LocalDate dateFrom);

    //    native sql (union)
    Page<SearchResultItem> searchVisitorsOrganisers(String lastName, String firstName, PageRequest pageRequest);

    void exportListOfActiveVisitors(HttpServletResponse response, LocalDate dateFrom, LocalDate dateTo) throws IOException;

    void exportListOfPastVisitors(HttpServletResponse response, LocalDate dateFrom) throws IOException;

    void exportListOfFutureVisitors(HttpServletResponse response, LocalDate dateTo) throws IOException;

}
