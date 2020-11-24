package ch.elca.visitors.persistence.entity;

import ch.elca.visitors.persistence.enumeration.Title;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VISITOR")
public class Visitor extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "title", nullable = false, length = 5)
    private Title title;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(length = 100)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 8)
    private Integer phoneNumber;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(length = 50)
    private String organization;

    @Column(name = "contact_person", length = 100)
    private String contactPerson;

    @Column(name = "reason_of_visit", nullable = false, length = 150)
    private String reasonOfVisit;

    @Min(value = 20, message = "Badge number should not be less than 20")
    @Max(value = 20, message = "Badge number should not be greater than 18")
    @Column(name = "badge_number", nullable = false, length = 20)
    private String badgeNumber;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false, precision = 1)
    private Double temperature;

    @Column(name = "checked_out_time")
    private LocalDateTime checkedOut;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "visitor_type_id", nullable = false)
    private VisitorType visitorType;

}
