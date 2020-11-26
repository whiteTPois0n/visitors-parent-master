package ch.elca.visitors.service.mapper;

import ch.elca.visitors.persistence.entity.MeetingType;
import ch.elca.visitors.service.dto.MeetingTypeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeetingTypeMapper {

    MeetingTypeDto mapToDto(MeetingType meetingType);

    MeetingType mapToEntity(MeetingTypeDto meetingTypeDto);
}
