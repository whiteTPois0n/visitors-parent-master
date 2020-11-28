package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.repository.FactoryRepository;
import ch.elca.visitors.persistence.repository.MeetingTypeRepository;
import ch.elca.visitors.persistence.repository.OrganiserRepository;
import ch.elca.visitors.service.dto.OrganiserDto;
import ch.elca.visitors.service.exception.ResourceNotFoundException;
import ch.elca.visitors.service.mapper.OrganiserMapper;
import ch.elca.visitors.service.service.OrganiserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganiserServiceImpl implements OrganiserService {

    private final OrganiserRepository organiserRepository;
    private final FactoryRepository factoryRepository;
    private final MeetingTypeRepository meetingTypeRepository;

    private final OrganiserMapper organiserMapper;


    public OrganiserDto addOrganiser(OrganiserDto organiserDto) {
        var organiser = organiserMapper.mapToOrganiser(organiserDto);
        factoryRepository.findById(organiserDto.getFactory().getId()).orElseThrow(() -> new ResourceNotFoundException("Incorrect factory id"));
        meetingTypeRepository.findById(organiserDto.getMeetingType().getId()).orElseThrow(() -> new ResourceNotFoundException("Incorrect meeting type id"));

        var savedOrganiser = organiserRepository.save(organiser);
        return organiserMapper.mapToOrganiserDto(savedOrganiser);
    }


    public List<OrganiserDto> getAllOrganisers() {
        var organisers = organiserRepository.findAll();
        return organisers
                .stream()
                .map(organiserMapper::mapToOrganiserDto)
                .collect(Collectors.toList());
    }


    public OrganiserDto findOrganiser(Long id) {
        var organiser = organiserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, organiser with id " + id + " not found"));
        return organiserMapper.mapToOrganiserDto(organiser);
    }


    public OrganiserDto updateOrganiser(OrganiserDto organiserDto) {
        var checkFactory = factoryRepository.findById(organiserDto.getFactory().getId()).orElseThrow(() -> new ResourceNotFoundException("Incorrect factory id"));
        var checkMeetingType = meetingTypeRepository.findById(organiserDto.getMeetingType().getId()).orElseThrow(() -> new ResourceNotFoundException("Incorrect meeting type id"));

        return organiserRepository.findById(organiserDto.getId())
                .map(organiser -> {
                    organiser.setFirstName(organiserDto.getFirstName());
                    organiser.setLastName(organiserDto.getLastName());
                    organiser.setJobTitle(organiserDto.getJobTitle());
                    organiser.setFactory(checkFactory);
                    organiser.setContactPerson(organiserDto.getContactPerson());
                    organiser.setContactPersonVisa(organiserDto.getContactPersonVisa());
                    organiser.setDateTime(organiserDto.getDateTime());
                    organiser.setMeetingRoom(organiserDto.getMeetingRoom());
                    organiser.setMeetingType(checkMeetingType);
                    return organiserMapper.mapToOrganiserDto(organiserRepository.save(organiser));
                }).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, organiser with id " + organiserDto.getId() + " not found"));
    }


    public void deleteOrganiser(Long id) {
        var existingOrganiser = organiserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, visitor with id " + id + " not found"));
        organiserRepository.delete(existingOrganiser);
    }

}