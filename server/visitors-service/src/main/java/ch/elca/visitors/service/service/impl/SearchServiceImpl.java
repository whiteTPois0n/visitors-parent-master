package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.entity.Organiser;
import ch.elca.visitors.persistence.entity.QOrganiser;
import ch.elca.visitors.persistence.entity.QVisitor;
import ch.elca.visitors.persistence.entity.SearchResultItem;
import ch.elca.visitors.persistence.entity.Visitor;
import ch.elca.visitors.persistence.nativesql.Query;
import ch.elca.visitors.persistence.repository.OrganiserRepository;
import ch.elca.visitors.persistence.repository.VisitorRepository;
import ch.elca.visitors.service.Utils.IteratorUtil;
import ch.elca.visitors.service.dto.OrganiserDto;
import ch.elca.visitors.service.dto.SearchDto;
import ch.elca.visitors.service.excel.ExcelExporter;
import ch.elca.visitors.service.exception.ResourceNotFoundException;
import ch.elca.visitors.service.mapper.OrganiserMapper;
import ch.elca.visitors.service.mapper.VisitorMapper;
import ch.elca.visitors.service.service.SearchService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final VisitorRepository visitorRepository;
    private final OrganiserRepository organiserRepository;

    private final VisitorMapper visitorMapper;
    private final OrganiserMapper organiserMapper;

    private final NamedParameterJdbcTemplate jdbcTemplate;


    /*    Query visitors and organisers given either firstName or Lastname or both
               Return a list of visitors and organiser sorted by
               1. current dates[asc],
               2. future dates [asc],
               3. past dates[desc]

        */
    public List<SearchDto> filterByLastNameAndFirstName(String lastName, String firstName) {
        var visitors = IteratorUtil.toList(visitorRepository.findAll(buildVisitorPredicate(lastName, firstName)));
        var organisers = IteratorUtil.toList(organiserRepository.findAll(buildOrganiserPredicate(lastName, firstName)));
        var searchDtos = buildSearchDtoList(visitors, organisers);
        var filteredInThefuture = filterInTheFuture(searchDtos);
        var filteredInThePast = filterInThePast(searchDtos);

        return Stream.of(filteredInThefuture, filteredInThePast)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }


    private BooleanBuilder buildVisitorPredicate(String lastName, String firstName) {
        var predicate = new BooleanBuilder();

        if (Objects.nonNull(lastName)) {
            predicate.and(QVisitor.visitor.lastName.containsIgnoreCase(lastName));
        }

        if (Objects.nonNull(firstName)) {
            predicate.and(QVisitor.visitor.firstName.containsIgnoreCase(firstName));
        }


        return predicate;
    }

    private BooleanBuilder buildOrganiserPredicate(String lastName, String firstName) {
        var predicate = new BooleanBuilder();

        if (Objects.nonNull(lastName)) {
            predicate.and(QOrganiser.organiser.lastName.containsIgnoreCase(lastName));
        }

        if (Objects.nonNull(firstName)) {
            predicate.and(QOrganiser.organiser.firstName.containsIgnoreCase(firstName));
        }


        return predicate;
    }

    private List<SearchDto> buildSearchDtoList(List<Visitor> visitors, List<Organiser> organisers) {
//        Map visitor list to visitor Dto list then further map to SearchDto list: fields[fullName, status, checkedIn, BadgeNumber]
        List<SearchDto> visitorsResult = visitors.stream()
                .map(visitorMapper::mapToSearchDto)
                .collect(Collectors.toList());

//        Map organiser list to organiser Dto list then further map to SearchDto list: fields[fullName, status, checkedIn, BadgeNumber]
        List<SearchDto> organisersResult = organisers.stream()
                .map(organiserMapper::mapToSearchDto)
                .collect(Collectors.toList());

        List<SearchDto> results = new ArrayList<>();
        results.addAll(visitorsResult);
        results.addAll(organisersResult);

        return results;
    }

    private List<SearchDto> filterInTheFuture(List<SearchDto> searchDtos) {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        Predicate<SearchDto> futurePredicate = searchDto -> searchDto.getDateTime().isAfter(dateTimeNow);

        return searchDtos.stream()
                .filter(futurePredicate)
                .sorted(Comparator.comparing(SearchDto::getDateTime))
                .collect(Collectors.toList());
    }

    private List<SearchDto> filterInThePast(List<SearchDto> searchDtos) {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        Predicate<SearchDto> pastPredicate = searchDto -> searchDto.getDateTime().isBefore(dateTimeNow);

        return searchDtos.stream()
                .filter(pastPredicate)
                .sorted(Comparator.comparing(SearchDto::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    /*     Query active visitors from visitor table given either dateFrom or dateTo or both
                Return a page of active visitors sorted by
                1. dateTime[asc]

    */
    public Page<SearchDto> generateListOfActiveVisitors(PageRequest pageRequest, LocalDate dateFrom, LocalDate dateTo) {
        var visitor = QVisitor.visitor;

        BooleanExpression visitorsByDateFromAndDateTo = Expressions.asBoolean(true).isTrue();

        if (Objects.nonNull(dateFrom) && Objects.nonNull(dateTo)) {
//            For queries where dateFrom and dateTo are the same
            var fromDateTime = dateFrom.atTime(0, 0);
            var toDateTime = dateTo.atTime(23, 59);

            visitorsByDateFromAndDateTo = visitor.checkedIn.between(LocalDateTime.from(fromDateTime), LocalDateTime.from(toDateTime))
                    .and(visitor.status.eq(true));
        }

        if (Objects.isNull(dateFrom) && Objects.isNull(dateTo)) {
            throw new ResourceNotFoundException("Oops something went wrong, query fields can't be null");
        }

        var visitors = visitorRepository.findAll(visitorsByDateFromAndDateTo, pageRequest);


//         Map Page<Visitor> to Page<SearchDto> and return
        return visitors
                .map(visitorMapper::mapToSearchDto);
    }


    /*     Query future visitors from organiser table given either dateTo
                Return a page of future visitors sorted by
                1. dateTime[asc]

    */
    public Page<SearchDto> generateListOfFutureVisitors(PageRequest pageRequest, LocalDate dateTo) {
        var organiser = QOrganiser.organiser;

        BooleanExpression organisersByDateFromAndDateTo = Expressions.asBoolean(true).isTrue();

        if (Objects.nonNull(dateTo)) {
            var dateTimeNow = LocalDateTime.now();
            var dateTimeTo = dateTo.atTime(23, 59);
            organisersByDateFromAndDateTo = organisersByDateFromAndDateTo.and(organiser.dateTime.between(LocalDateTime.from(dateTimeNow), LocalDateTime.from(dateTimeTo)));
        }

        var organisers = organiserRepository
                .findAll(organisersByDateFromAndDateTo, pageRequest);

        return organisers
                .map(organiserMapper::mapToSearchDto);
    }


    /*     Query past visitors from visitor table given dateFrom
                Return a page of past visitors sorted by
                1. dateTime[asc]

    */
    public Page<SearchDto> generateListOfPastVisitors(PageRequest pageRequest, LocalDate dateFrom) {
        var visitor = QVisitor.visitor;

        BooleanExpression visitorsByDateFromAndDateTo = Expressions.asBoolean(true).isTrue();

        if (Objects.nonNull(dateFrom)) {
            var dateTimeNow = LocalDateTime.now();
            var dateTimeFrom = dateFrom.atTime(0, 0);

            visitorsByDateFromAndDateTo = visitorsByDateFromAndDateTo.and(visitor.checkedIn.between(LocalDateTime.from(dateTimeFrom), LocalDateTime.from(dateTimeNow)));
        }

        var visitors = visitorRepository.findAll(visitorsByDateFromAndDateTo, pageRequest);

        return visitors
                .map(visitorMapper::mapToSearchDto);
    }


    public List<OrganiserDto> filterOrganisersByLastNameAndFirstName(String lastName, String firstName) {

        var organisers = IteratorUtil.toList(organiserRepository.findAll(buildOrganiserPredicate(lastName, firstName)));

        return organisers.stream()
                .map(organiserMapper::mapToOrganiserDto)
                .sorted(Comparator.comparing(OrganiserDto::getDateTime))
                .collect(Collectors.toList());
    }


    @Override
    public Page<SearchResultItem> searchVisitorsOrganisers(String lastName, String firstName, PageRequest pageRequest) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("firstName", firstName);
        paramsMap.put("lastName", lastName);
        Integer totalNoItems = jdbcTemplate.queryForObject(Query.SEARCH_VISITOR_ORGANIZER_COUNT_SQL, paramsMap, Integer.class);

        // Ensure that the second query gets executed only when no of items not null and greater than 0
        if (totalNoItems != null && totalNoItems > 0) {
            List<SearchResultItem> searchResultItems = visitorRepository.search(firstName, lastName, pageRequest.getPageSize(), pageRequest.getOffset());
            return new PageImpl<>(searchResultItems, pageRequest, totalNoItems);
        }
        return Page.empty();
    }


    public void exportListOfActiveVisitors(HttpServletResponse response, LocalDate dateFrom, LocalDate dateTo) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=active-visitors.xlsx";

        response.setHeader(headerKey, headerValue);

        var visitor = QVisitor.visitor;

        BooleanExpression visitorsByDateFromAndDateTo = Expressions.asBoolean(true).isTrue();

        if (Objects.nonNull(dateFrom) && Objects.nonNull(dateTo)) {
//            For queries where dateFrom and dateTo are the same
            var fromDateTime = dateFrom.atTime(0, 0);
            var toDateTime = dateTo.atTime(23, 59);

            visitorsByDateFromAndDateTo = visitor.checkedIn.between(LocalDateTime.from(fromDateTime), LocalDateTime.from(toDateTime))
                    .and(visitor.status.eq(true));
        }

        if (Objects.isNull(dateFrom) && Objects.isNull(dateTo)) {
            throw new ResourceNotFoundException("Oops something went wrong, query fields can't be null");
        }

        var visitors = IteratorUtil.toList(visitorRepository.findAll(visitorsByDateFromAndDateTo));

        var searchDtos = visitors.stream()
                .map(visitorMapper::mapToSearchDto)
                .sorted(Comparator.comparing(SearchDto::getDateTime))
                .collect(Collectors.toList());

        ExcelExporter excelExporter = new ExcelExporter(searchDtos);
        excelExporter.export(response);
    }


    public void exportListOfPastVisitors(HttpServletResponse response, LocalDate dateFrom) throws IOException {
        var visitor = QVisitor.visitor;

        BooleanExpression visitorsByDateFromAndDateTo = Expressions.asBoolean(true).isTrue();

        if (Objects.nonNull(dateFrom)) {
            var dateTimeNow = LocalDateTime.now();
            var dateTimeFrom = dateFrom.atTime(0, 0);

            visitorsByDateFromAndDateTo = visitorsByDateFromAndDateTo.and(visitor.checkedIn.between(LocalDateTime.from(dateTimeFrom), LocalDateTime.from(dateTimeNow)));
        }

        var visitors = IteratorUtil.toList(visitorRepository.findAll(visitorsByDateFromAndDateTo));

        var searchDtos = visitors.stream()
                .map(visitorMapper::mapToSearchDto)
                .sorted(Comparator.comparing(SearchDto::getDateTime))
                .collect(Collectors.toList());

        ExcelExporter excelExporter = new ExcelExporter(searchDtos);
        excelExporter.export(response);
    }


    public void exportListOfFutureVisitors(HttpServletResponse response, LocalDate dateTo) throws IOException {
        var organiser = QOrganiser.organiser;

        BooleanExpression organisersByDateFromAndDateTo = Expressions.asBoolean(true).isTrue();

        if (Objects.nonNull(dateTo)) {
            var dateTimeNow = LocalDateTime.now();
            var dateTimeTo = dateTo.atTime(23, 59);
            organisersByDateFromAndDateTo = organisersByDateFromAndDateTo.and(organiser.dateTime.between(LocalDateTime.from(dateTimeNow), LocalDateTime.from(dateTimeTo)));
        }

        var organisers = (List<Organiser>) organiserRepository
                .findAll(organisersByDateFromAndDateTo);

        var searchDtos = organisers.stream()
                .map(organiserMapper::mapToSearchDto)
                .sorted(Comparator.comparing(SearchDto::getDateTime))
                .collect(Collectors.toList());

        ExcelExporter excelExporter = new ExcelExporter(searchDtos);
        excelExporter.export(response);
    }
}