package ch.elca.visitors.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "ORGANISER")
public class Organiser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "job_title", nullable = false, length = 100)
    private String jobTitle;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "factory_id", nullable = false)
    private Factory factory;

    @Column(name = "contact_person", nullable = false, length = 100)
    private String contactPerson;

    @Column(name = "contact_person_visa", nullable = false, length = 3)
    private String contactPersonVisa;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "meeting_type_id", nullable = false)
    private MeetingType meetingType;

    @Column(name = "meeting_room", nullable = false, length = 11)
    private String meetingRoom;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "status", columnDefinition = "boolean default false")
    private Boolean status;

}
