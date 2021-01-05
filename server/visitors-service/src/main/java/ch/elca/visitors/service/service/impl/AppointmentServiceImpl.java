package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.entity.QAppointment;
import ch.elca.visitors.persistence.entity.QVisit;
import ch.elca.visitors.persistence.repository.AppointmentRepository;
import ch.elca.visitors.persistence.repository.ContactRepository;
import ch.elca.visitors.persistence.repository.VisitRepository;
import ch.elca.visitors.persistence.repository.VisitorRepository;
import ch.elca.visitors.service.dto.AppointmentDto;
import ch.elca.visitors.service.excel.AppointmentExporter;
import ch.elca.visitors.service.exception.ResourceNotFoundException;
import ch.elca.visitors.service.mapper.AppointmentMapper;
import ch.elca.visitors.service.service.AppointmentService;
import ch.elca.visitors.service.utils.IteratorUtil;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {


    public final AppointmentRepository appointmentRepository;
    public final VisitorRepository visitorRepository;
    public final VisitRepository visitRepository;
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


    public void deleteAppointment(Long id) {
        var qVisit = QVisit.visit;
        var predicate = new BooleanBuilder();

        visitRepository.findOne(predicate.and(qVisit.appointment.id.eq(id))).ifPresent(
                visit1 -> visit1.setAppointment(null));

        var existingAppointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, appointment with id " + id + " not found"));
        appointmentRepository.delete(existingAppointment);
    }


    public Page<AppointmentDto> getAllAppointments(PageRequest pageRequest) {
        var appointments = appointmentRepository.findAll(pageRequest);
        return appointments
                .map(appointmentMapper::mapToAppointmentDto);
    }


    public List<AppointmentDto> searchAppointmentsByVisitorNameOrEmail(String search) {
        var appointments = IteratorUtil.toList(appointmentRepository.findAll(buildSearchAppointmentsByVisitor(search)));

        return appointments.stream()
                .map(appointmentMapper::mapToAppointmentDto)
                .collect(Collectors.toList());
    }


    public Page<AppointmentDto> searchFutureVisitors(PageRequest pageRequest, LocalDate dateTo) {
        var appointments = appointmentRepository.findAll(buildSearchFutureVisitorsPredicate(dateTo), pageRequest);
        return appointments
                .map(appointmentMapper::mapToAppointmentDto);
    }


    public Page<AppointmentDto> searchAppointmentsByVisitorName(PageRequest pageRequest, String search) {
        var appointments = appointmentRepository.findAll(buildSearchAppointmentsByVisitorNamePredicate(search), pageRequest);
        return appointments
                .map(appointmentMapper::mapToAppointmentDto);
    }


    public void exportListOfFutureVisitors(HttpServletResponse response, LocalDate dateTo) throws IOException {

        var appointments = IteratorUtil.toList(appointmentRepository.findAll(buildSearchFutureVisitorsPredicate(dateTo)));

        var appointmentDtos = appointments
                .stream()
                .map(appointmentMapper::mapToAppointmentDto)
                .sorted(Comparator.comparing(AppointmentDto::getAppointmentDate))
                .collect(Collectors.toList());

        AppointmentExporter appointmentExporter = new AppointmentExporter(appointmentDtos);
        appointmentExporter.export(response);
    }


    private BooleanBuilder buildSearchAppointmentsByVisitor(String search) {
        var qAppointment = QAppointment.appointment;
        var predicate = new BooleanBuilder();

        if (Objects.nonNull(search)) {

            predicate
                    .and(qAppointment.visitor.lastName.containsIgnoreCase(search)
                            .or(qAppointment.visitor.firstName.containsIgnoreCase(search)
                                    .or(qAppointment.visitor.email.containsIgnoreCase(search))));
        }
        return predicate;
    }


    private BooleanBuilder buildSearchFutureVisitorsPredicate(LocalDate dateTo) {
        var qAppointment = QAppointment.appointment;
        var predicate = new BooleanBuilder();

        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime dateToWithTime = dateTo.atTime(23, 59, 59);

        predicate
                .and(qAppointment.appointmentDate.between(today, dateToWithTime));

        return predicate;
    }


    private BooleanBuilder buildSearchAppointmentsByVisitorNamePredicate(String search) {
        var qAppointment = QAppointment.appointment;
        var predicate = new BooleanBuilder();

        if (Objects.nonNull(search)) {
            predicate
                    .and(qAppointment.visitor.lastName.containsIgnoreCase(search))
                    .or(qAppointment.visitor.firstName.containsIgnoreCase(search));
        }

        return predicate;

    }

}
