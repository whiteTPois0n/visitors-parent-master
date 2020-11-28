package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.MeetingTypeDto;

import java.util.List;

public interface MeetingTypeService {

    void addMeetingType(MeetingTypeDto meetingTypeDto);

    List<MeetingTypeDto> getAllMeetingTypes();

    MeetingTypeDto findMeetingType(Long id);

    void updateMeetingType(MeetingTypeDto meetingTypeDto);

}
