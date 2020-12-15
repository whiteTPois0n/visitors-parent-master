package ch.elca.visitors.persistence.entity;

import ch.elca.visitors.persistence.enumeration.VisitorType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VISIT")
public class Visit extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "visitor_type", nullable = false, length = 50)
    private VisitorType visitorType;

    @Column(name = "reason_of_visit", length = 150)
    private String reasonOfVisit;

    @Column(name = "badge_number", nullable = false, length = 10)
    private String badgeNumber;

    @Column(nullable = false, precision = 1)
    private Double temperature;

    @Column(name = "checked_out_time")
    private LocalDateTime checkedOut;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "visitor_id", nullable = false)
    private Visitor visitor;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", name = "appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "contact_id")
    private Contact contact;

}
