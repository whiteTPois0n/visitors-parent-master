package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.entity.Organiser;
import ch.elca.visitors.persistence.entity.QOrganiser;
import ch.elca.visitors.persistence.entity.QVisitor;
import ch.elca.visitors.persistence.entity.Visitor;
import ch.elca.visitors.persistence.repository.OrganiserRepository;
import ch.elca.visitors.persistence.repository.VisitorRepository;
import ch.elca.visitors.persistence.repository.VisitorTypeRepository;
import ch.elca.visitors.service.dto.SearchDto;
import ch.elca.visitors.service.dto.VisitorDto;
import ch.elca.visitors.service.exception.ResourceNotFoundException;
import ch.elca.visitors.service.mapper.OrganiserMapper;
import ch.elca.visitors.service.mapper.VisitorMapper;
import ch.elca.visitors.service.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;
    private final VisitorMapper visitorMapper;
    private final OrganiserMapper organiserMapper;
    //    private final VisitorTypeMapper visitorTypeMapper;
    private final VisitorTypeRepository visitorTypeRepository;
    private final OrganiserRepository organiserRepository;


    public VisitorDto addVisitor(VisitorDto visitorDto) {
        var visitor = visitorMapper.mapToEntity(visitorDto);
        visitorTypeRepository.findById(visitorDto.getVisitorType().getId()).orElseThrow(() -> new ResourceNotFoundException("Incorrect visitor type id"));

        visitor.setStatus(true);
        var saved = visitorRepository.save(visitor);
        return visitorMapper.mapToDto(saved);
    }


    public List<VisitorDto> getAllVisitors() {
        var visitors = visitorRepository.findAll();
        return visitors
                .stream()
                .map(visitorMapper::mapToDto)
                .collect(Collectors.toList());
    }


    public VisitorDto findVisitor(Long id) {
        var visitor = visitorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, visitor with id " + id + " not found"));
        return visitorMapper.mapToDto(visitor);
    }


    public VisitorDto updateVisitor(VisitorDto visitorDto) {

        var checkVisitorType = visitorTypeRepository.findById(visitorDto.getVisitorType().getId()).orElseThrow(() -> new ResourceNotFoundException("Incorrect visitor type id"));


        return visitorRepository.findById(visitorDto.getId())
                .map(visitor -> {
                    visitor.setTitle(visitorDto.getTitle());
                    visitor.setFirstName(visitorDto.getFirstName());
                    visitor.setLastName(visitorDto.getLastName());
                    visitor.setVisitorType(checkVisitorType);
                    visitor.setEmail(visitorDto.getEmail());
                    visitor.setPhoneNumber(visitorDto.getPhoneNumber());
                    visitor.setAddress(visitorDto.getAddress());
                    visitor.setOrganization(visitorDto.getOrganization());
                    visitor.setContactPerson(visitorDto.getContactPerson());
                    visitor.setReasonOfVisit(visitorDto.getReasonOfVisit());
                    visitor.setTemperature(visitorDto.getTemperature());
                    visitor.setBadgeNumber(visitorDto.getBadgeNumber());

                    return visitorMapper.mapToDto(visitorRepository.save(visitor));
                }).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, visitor with id " + visitorDto.getId() + " not found"));
    }


    public void deleteVisitor(Long id) {
        var existingVisitor = visitorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, visitor with id " + id + " not found"));
        visitorRepository.delete(existingVisitor);
    }

    @Override
    public List<SearchDto> findVisitorByLastNameAndFirstName(String lastName, String firstName) {

        var visitor = QVisitor.visitor;
        var organiser = QOrganiser.organiser;
        var findVisitorsByLastNameAndFirstName = visitor.lastName.eq(lastName).or(visitor.firstName.eq(firstName));
        var findOrganisersByLastNameAndFirstName = organiser.lastName.eq(lastName).or(organiser.firstName.eq(firstName));

        var visitors = (List<Visitor>) visitorRepository.findAll(findVisitorsByLastNameAndFirstName);
        var organisers = (List<Organiser>) organiserRepository.findAll(findOrganisersByLastNameAndFirstName);

        List<SearchDto> visitorsResult = visitors.stream()
                // sort checked in in asc
//                .sorted(Comparator.comparing(Visitor::getCheckedIn))
                .map(visitorMapper::mapToDto)
                .map(v -> new SearchDto(v.getFirstName(), v.getLastName(), v.getStatus(), v.getCheckedIn()))
                .collect(Collectors.toList());
//

        List<SearchDto> organisersResult = organisers.stream()
                // sort checked in in asc
//                .sorted(Comparator.comparing(Organiser::getDateTime))
                .map(organiserMapper::mapToDto)
                .map(o -> new SearchDto(o.getFirstName(), o.getLastName(), o.getStatus(), o.getDateTime()))
                .collect(Collectors.toList());

        List<SearchDto> results = new ArrayList<>();
        results.addAll(visitorsResult);
        results.addAll(organisersResult);


//        todo (or create custom comparator)

        LocalDateTime dateTimeNow = LocalDateTime.now();

        Predicate<SearchDto> futurePredicate = searchDto -> searchDto.getDateTime().isAfter(dateTimeNow);
        Predicate<SearchDto> pastPredicate = searchDto -> searchDto.getDateTime().isBefore(dateTimeNow);

        List<SearchDto> futureResults = results.stream()
                .filter(futurePredicate)
                .sorted(Comparator.comparing(SearchDto::getDateTime))
                .collect(Collectors.toList());

        List<SearchDto> pastResults = results.stream()
                .filter(pastPredicate)
                .collect(Collectors.toList());

        List<SearchDto> sortedResults = Stream.of(futureResults, pastResults)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return sortedResults;
    }

}
