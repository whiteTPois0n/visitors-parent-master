package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.entity.QVisitor;
import ch.elca.visitors.persistence.repository.VisitorRepository;
import ch.elca.visitors.service.Utils.IteratorUtil;
import ch.elca.visitors.service.dto.VisitorDto;
import ch.elca.visitors.service.exception.ResourceNotFoundException;
import ch.elca.visitors.service.mapper.VisitorMapper;
import ch.elca.visitors.service.service.VisitorService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;

    private final VisitorMapper visitorMapper;


    public VisitorDto addVisitor(VisitorDto visitorDto) {
        var visitor = visitorMapper.mapToVisitor(visitorDto);
        var saved = visitorRepository.save(visitor);

        return visitorMapper.mapToVisitorDto(saved);
    }


    public List<VisitorDto> getAllVisitors() {
        var visitors = visitorRepository.findAll();
        return visitors
                .stream()
                .map(visitorMapper::mapToVisitorDto)
                .collect(Collectors.toList());
    }


    public VisitorDto findVisitor(Long id) {
        var visitor = visitorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, visitor with id " + id + " not found"));
        return visitorMapper.mapToVisitorDto(visitor);
    }


    public VisitorDto updateVisitor(VisitorDto visitorDto) {

        return visitorRepository.findById(visitorDto.getId())
                .map(visitor -> {
                    visitor.setTitle(visitorDto.getTitle());
                    visitor.setFirstName(visitorDto.getFirstName());
                    visitor.setLastName(visitorDto.getLastName());
                    visitor.setEmail(visitorDto.getEmail());
                    visitor.setPhoneNumber(visitorDto.getPhoneNumber());
                    visitor.setAddress(visitorDto.getAddress());
                    visitor.setOrganization(visitorDto.getOrganization());
                    return visitorMapper.mapToVisitorDto(visitorRepository.save(visitor));

                }).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, visitor with id " + visitorDto.getId() + " not found"));
    }


    public void deleteVisitor(Long id) {
        var existingVisitor = visitorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, visitor with id " + id + " not found"));
        visitorRepository.delete(existingVisitor);
    }


    public List<VisitorDto> searchVisitorByNameOrEmail(String firstName, String lastName, String email) {

        var visitors = IteratorUtil.toList(visitorRepository.findAll(buildVisitorPredicate(firstName, lastName, email)));

        return visitors.stream()
                .map(visitorMapper::mapToVisitorDto)
                .collect(Collectors.toList());
    }

    private BooleanBuilder buildVisitorPredicate(String firstName, String lastName, String email) {
        var qVisitor = QVisitor.visitor;
        var predicate = new BooleanBuilder();

        if (Objects.nonNull(firstName) && Objects.nonNull(lastName) && Objects.nonNull(email)) {
            predicate.and(qVisitor.firstName.equalsIgnoreCase(firstName).or(qVisitor.lastName.equalsIgnoreCase(lastName)).or(qVisitor.email.eq(email)));
        }

        return predicate;
    }
}