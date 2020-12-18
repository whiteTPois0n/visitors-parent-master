package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.entity.QAppointment;
import ch.elca.visitors.persistence.repository.AppointmentRepository;
import ch.elca.visitors.persistence.repository.ContactRepository;
import ch.elca.visitors.persistence.repository.VisitorRepository;
import ch.elca.visitors.service.Utils.IteratorUtil;
import ch.elca.visitors.service.dto.AppointmentDto;
import ch.elca.visitors.service.exception.ResourceNotFoundException;
import ch.elca.visitors.service.mapper.AppointmentMapper;
import ch.elca.visitors.service.service.AppointmentService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {


    public final AppointmentRepository appointmentRepository;
    public final VisitorRepository visitorRepository;
    public final ContactRepository contactRepository;
    public final AppointmentMapper appointmentMapper;


    public AppointmentDto addAppointment(AppointmentDto appointmentDto) {

        visitorRepository.findById(appointmentDto.getVisitor().getId()).orElseThrow(() -> new ResourceNotFoundException("No visitor found with id " +
                appointmentDto.getVisitor().getId()));

        contactRepository.findById(appointmentDto.getContact().getId()).orElseThrow(() -> new ResourceNotFoundException("No contact found with id " +
                appointmentDto.getContact().getId()));

        var appointment = appointmentMapper.mapToAppointment(appointmentDto);
        var saved = appointmentRepository.save(appointment);
        return appointmentMapper.mapToAppointmentDto(saved);
    }


    public AppointmentDto findAppointment(Long id) {
        var appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, appointment with id " + id + " not found"));
        return appointmentMapper.mapToAppointmentDto(appointment);
    }


    public List<AppointmentDto> searchAppointmentByVisitorFirstNameOrLastNameOrEmail(String search) {
        var appointments = IteratorUtil.toList(appointmentRepository.findAll(buildAppointmentPredicate(search)));
        return appointments.stream()
                .map(appointmentMapper::mapToAppointmentDto)
                .collect(Collectors.toList());
    }


    public BooleanBuilder buildAppointmentPredicate(String search) {
        var qAppointment = QAppointment.appointment;
        var predicate = new BooleanBuilder();

        if (Objects.nonNull(search)) {
            predicate.and(qAppointment.visitor.firstName.containsIgnoreCase(search).or(qAppointment.visitor.lastName.containsIgnoreCase(search)
                    .or(qAppointment.visitor.email.containsIgnoreCase(search))));
        }

        return predicate;
    }

}
