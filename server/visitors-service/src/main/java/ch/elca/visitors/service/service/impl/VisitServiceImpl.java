package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.repository.AppointmentRepository;
import ch.elca.visitors.persistence.repository.ContactRepository;
import ch.elca.visitors.persistence.repository.VisitRepository;
import ch.elca.visitors.persistence.repository.VisitorRepository;
import ch.elca.visitors.service.dto.VisitDto;
import ch.elca.visitors.service.exception.ResourceNotFoundException;
import ch.elca.visitors.service.mapper.VisitMapper;
import ch.elca.visitors.service.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final AppointmentRepository appointmentRepository;
    private final VisitorRepository visitorRepository;

    private final VisitMapper visitMapper;

    public VisitDto addVisit(VisitDto visitDto) {


//Todo if visitorDto has visitId appointmentId, check visitId & appointmentId

//        visitorRepository.findById(visitDto.getVisitor().getId()).orElseThrow(() -> new ResourceNotFoundException("No visitor found with id " +
//                visitDto.getVisitor().getId()));
//
//        appointmentRepository.findById(visitDto.getAppointment().getId()).orElseThrow(() -> new ResourceNotFoundException("No appointment found with id " +
//                visitDto.getAppointment().getId()));

        var visit = visitMapper.mapToVisit(visitDto);
        var saved = visitRepository.save(visit);
        return visitMapper.mapToVisitDto(saved);
    }

}
