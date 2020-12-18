package ch.elca.visitors.service.dto;

import ch.elca.visitors.persistence.entity.Contact;
import ch.elca.visitors.persistence.entity.Visitor;
import ch.elca.visitors.persistence.enumeration.Factory;
import ch.elca.visitors.persistence.enumeration.MeetingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private Long id;

    private String jobTitle;

    private Factory factory;

    private MeetingType meetingType;

    private String meetingRoom;

    private LocalDateTime appointmentDate;

    private ContactDto contact;

    private VisitorDto visitor;
}
