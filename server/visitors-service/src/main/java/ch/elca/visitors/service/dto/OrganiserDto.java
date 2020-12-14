package ch.elca.visitors.service.dto;

import ch.elca.visitors.persistence.entity.Factory;
import ch.elca.visitors.persistence.entity.MeetingType;
import ch.elca.visitors.persistence.entity.Visitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrganiserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String jobTitle;

    private Factory factory;

    private String contactPerson;

    private String contactPersonVisa;

    private MeetingType meetingType;

    private String meetingRoom;

    private LocalDateTime dateTime;

    private Boolean status;

//    private List<Visitor> visitors;

}
