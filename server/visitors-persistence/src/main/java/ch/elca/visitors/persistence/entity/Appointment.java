package ch.elca.visitors.persistence.entity;


import ch.elca.visitors.persistence.enumeration.Factory;
import ch.elca.visitors.persistence.enumeration.MeetingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "APPOINTMENT")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "job_title", length = 100)
    private String jobTitle;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Factory factory;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "meeting_type", nullable = false, length = 50)
    private MeetingType meetingType;

    @Column(name = "meeting_room", nullable = false, length = 11)
    private String meetingRoom;

    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "contact_id", nullable = false)
    private Contact contact;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "visitor_id", nullable = false)
    private Visitor visitor;

}
