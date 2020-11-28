package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.repository.MeetingTypeRepository;
import ch.elca.visitors.service.dto.MeetingTypeDto;
import ch.elca.visitors.service.exception.ResourceNotFoundException;
import ch.elca.visitors.service.mapper.MeetingTypeMapper;
import ch.elca.visitors.service.service.MeetingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingTypeServiceImpl implements MeetingTypeService {

    private final MeetingTypeRepository meetingTypeRepository;
    private final MeetingTypeMapper meetingTypeMapper;


    public void addMeetingType(MeetingTypeDto meetingTypeDto) {
        var meetingType = meetingTypeMapper.mapToMeetingType(meetingTypeDto);
        meetingTypeRepository.save(meetingType);
    }


    public List<MeetingTypeDto> getAllMeetingTypes() {
        var meetingTypes = meetingTypeRepository.findAll();
        return meetingTypes
                .stream()
                .map(meetingTypeMapper::mapToMeetingTypeDto)
                .collect(Collectors.toList());
    }


    public MeetingTypeDto findMeetingType(Long id) {
        var meetingType = meetingTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, meeting type with id " + id + " not found"));
        return meetingTypeMapper.mapToMeetingTypeDto(meetingType);
    }


    public void updateMeetingType(MeetingTypeDto meetingTypeDto) {
        meetingTypeRepository.findById(meetingTypeDto.getId())
                .map(meetingType -> {
                    meetingType.setMeetingType(meetingTypeDto.getMeetingType());
                    return meetingTypeRepository.save(meetingType);
                }).orElseThrow(() -> new ResourceNotFoundException("Oops something went wrong, meeting type with id " + meetingTypeDto.getId() + " not found"));
    }

}
