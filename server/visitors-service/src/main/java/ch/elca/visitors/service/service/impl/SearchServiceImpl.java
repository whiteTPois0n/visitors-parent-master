package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.entity.Organiser;
import ch.elca.visitors.persistence.entity.QOrganiser;
import ch.elca.visitors.persistence.entity.QVisitor;
import ch.elca.visitors.persistence.entity.Visitor;
import ch.elca.visitors.persistence.repository.OrganiserRepository;
import ch.elca.visitors.persistence.repository.VisitorRepository;
import ch.elca.visitors.service.dto.SearchDto;
import ch.elca.visitors.service.mapper.OrganiserMapper;
import ch.elca.visitors.service.mapper.VisitorMapper;
import ch.elca.visitors.service.service.SearchService;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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


    /*    Query visitors and organisers given either firstName or Lastname or both
               Return a list of visitors and organiser sorted by
               1. current dates[asc],
               2. future dates [asc],
               3. past dates[desc]

        */
    public List<SearchDto> filterByLastNameAndFirstName(String lastName, String firstName) {

        var visitor = QVisitor.visitor;
        var organiser = QOrganiser.organiser;

        BooleanExpression visitorsByLastNameAndFirstName = Expressions.asBoolean(true).isTrue();
        BooleanExpression organisersByLastNameAndFirstName = Expressions.asBoolean(true).isTrue();

//        Using only lastName field
        if (Objects.nonNull(lastName)) {
            visitorsByLastNameAndFirstName = visitorsByLastNameAndFirstName.and(visitor.lastName.containsIgnoreCase(lastName));
            organisersByLastNameAndFirstName = organisersByLastNameAndFirstName.and(organiser.lastName.containsIgnoreCase(lastName));
        }

//        Using only firstName field
        if (Objects.nonNull(firstName)) {
            visitorsByLastNameAndFirstName = visitorsByLastNameAndFirstName.and(visitor.firstName.containsIgnoreCase(firstName));
            organisersByLastNameAndFirstName = organisersByLastNameAndFirstName.and(organiser.firstName.containsIgnoreCase(firstName));
        }

        // SubQueryExpression<Tuple> subQueryExpression =


        var visitors = (List<Visitor>) visitorRepository.findAll(visitorsByLastNameAndFirstName);
        var organisers = (List<Organiser>) organiserRepository.findAll(organisersByLastNameAndFirstName);


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

        LocalDateTime dateTimeNow = LocalDateTime.now();

        Predicate<SearchDto> futurePredicate = searchDto -> searchDto.getDateTime().isAfter(dateTimeNow);
        Predicate<SearchDto> pastPredicate = searchDto -> searchDto.getDateTime().isBefore(dateTimeNow);

        List<SearchDto> futureResults = results.stream()
                .filter(futurePredicate)
                .sorted(Comparator.comparing(SearchDto::getDateTime))
                .collect(Collectors.toList());

        List<SearchDto> pastResults = results.stream()
                .filter(pastPredicate)
                .sorted(Comparator.comparing(SearchDto::getDateTime).reversed())
                .collect(Collectors.toList());

        return Stream.of(futureResults, pastResults)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

    }


    /*    Query active visitors from visitor table given either dateFrom or dateTo or both
           Return a list of active visitors sorted by
           1. dateTime[asc]

    */
    public Page<SearchDto> generateListOfCurrentVisitors(LocalDate dateFrom, LocalDate dateTo) {
        var visitor = QVisitor.visitor;

        BooleanExpression visitorsByDateFromAndDateTo = Expressions.asBoolean(true).isTrue();

//        Using only dateFrom field
        if (Objects.nonNull(dateFrom)) {
            var dateTimeFrom = dateFrom.atTime(0, 0);

            visitorsByDateFromAndDateTo = visitorsByDateFromAndDateTo.and(visitor.checkedIn.after(LocalDateTime.from(dateTimeFrom)).and(visitor.status.eq(true)));
        }

//        Using only dateTo field
        if (Objects.nonNull(dateTo)) {
            var dateTimeTo = dateTo.atTime(23, 59);

            visitorsByDateFromAndDateTo = visitorsByDateFromAndDateTo.and(visitor.checkedIn.before(LocalDateTime.from(dateTimeTo)).and(visitor.status.eq(true)));
        }

//        Using both dateFrom and dateTo field
        if (Objects.nonNull(dateFrom) && Objects.nonNull(dateTo)) {
//            For queries where dateFrom and dateTo are the same
            var fromDateTime = dateFrom.atTime(0, 0);
            var toDateTime = dateTo.atTime(23, 59);

            visitorsByDateFromAndDateTo = visitor.checkedIn.between(LocalDateTime.from(fromDateTime), LocalDateTime.from(toDateTime))
                    .and(visitor.status.eq(true));
        }


        var pageRequest = PageRequest.of(1,10,Sort.by(Sort.Direction.ASC, visitor.checkedIn.getMetadata().getName()));

        var visitors = visitorRepository.findAll(visitorsByDateFromAndDateTo, pageRequest);


//         Map visitor list to visitor Dto list then further map to SearchDto list: fields[fullName, status, checkedIn, BadgeNumber]
        return visitors
                .map(visitorMapper::mapToSearchDto);

    }


    /*    Query future visitors from organiser table given either dateTo
           Return a list of future visitors sorted by
           1. dateTime[asc]

    */
    public List<SearchDto> generateListOfFutureVisitors(LocalDate dateTo) {
        var organiser = QOrganiser.organiser;

        BooleanExpression organisersByDateFromAndDateTo = Expressions.asBoolean(true).isTrue();

//        Using only dateTo field
        if (Objects.nonNull(dateTo)) {
            var dateTimeNow = LocalDateTime.now();
            var dateTimeTo = dateTo.atTime(23, 59);
            organisersByDateFromAndDateTo = organisersByDateFromAndDateTo.and(organiser.dateTime.between(LocalDateTime.from(dateTimeNow), LocalDateTime.from(dateTimeTo)));
        }

        var organisers = (List<Organiser>) organiserRepository.findAll(organisersByDateFromAndDateTo);

//        Map organiser list to organiser Dto list then further map to SearchDto list: fields[fullName, status, checkedIn, BadgeNumber]
        return organisers.stream()
                .map(organiserMapper::mapToSearchDto)
                .sorted(Comparator.comparing(SearchDto::getDateTime))
                .collect(Collectors.toList());

    }


    /*    Query past visitors from visitor table given dateFrom
           Return a list of past visitors sorted by
           1. dateTime[asc]

    */
    public List<SearchDto> generateListOfPastVisitors(LocalDate dateFrom) {
        var visitor = QVisitor.visitor;

        BooleanExpression visitorsByDateFromAndDateTo = Expressions.asBoolean(true).isTrue();

        //        Using only dateFrom field
        if (Objects.nonNull(dateFrom)) {
            var dateTimeNow = LocalDateTime.now();
            var dateTimeFrom = dateFrom.atTime(0, 0);

            visitorsByDateFromAndDateTo = visitorsByDateFromAndDateTo.and(visitor.checkedIn.between(LocalDateTime.from(dateTimeFrom), LocalDateTime.from(dateTimeNow)));
        }

        var visitors = (List<Visitor>) visitorRepository.findAll(visitorsByDateFromAndDateTo);


//         Map visitor list to visitor Dto list then further map to SearchDto list: fields[fullName, status, checkedIn, BadgeNumber]
        return visitors.stream()
                .map(visitorMapper::mapToSearchDto)
                .sorted(Comparator.comparing(SearchDto::getDateTime))
                .collect(Collectors.toList());

    }

}
