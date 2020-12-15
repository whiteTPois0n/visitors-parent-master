package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.entity.QVisitor;
import ch.elca.visitors.persistence.repository.VisitorRepository;
import ch.elca.visitors.persistence.repository.VisitorTypeRepository;
import ch.elca.visitors.service.dto.VisitorDto;
import ch.elca.visitors.service.exception.ResourceNotFoundException;
import ch.elca.visitors.service.mapper.VisitorMapper;
import ch.elca.visitors.service.service.VisitorService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;
    private final VisitorTypeRepository visitorTypeRepository;

    private final VisitorMapper visitorMapper;


    public VisitorDto addVisitor(VisitorDto visitorDto) {

        visitorTypeRepository.findById(visitorDto.getVisitorType().getId()).orElseThrow(() -> new ResourceNotFoundException("Incorrect visitor type id"));

        var visitor = visitorMapper.mapToVisitor(visitorDto);
        visitor.setStatus(true);

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

                    return visitorMapper.mapToVisitorDto(visitorRepository.save(visitor));

                }).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, visitor with id " + visitorDto.getId() + " not found"));
    }


    public void deleteVisitor(Long id) {
        var existingVisitor = visitorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, visitor with id " + id + " not found"));
        visitorRepository.delete(existingVisitor);
    }


    public String verifyVisitorBadgeNumberAndCheckout(String badgeNumber, String email) {

        var qVisitor = QVisitor.visitor;
        var predicate = new BooleanBuilder();

        predicate.and(qVisitor.email.eq(email).and(qVisitor.badgeNumber.eq(badgeNumber)));

        var visitor = (visitorRepository.findOne(predicate).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, visitor with badge number " + badgeNumber + " not found")));
        LocalDateTime localDateTime = LocalDateTime.now();
        visitor.setCheckedOut(localDateTime);
        visitorRepository.save(visitor);
        return "Checkout successful";
    }

}