package ch.elca.visitors.persistence.entity;

import ch.elca.visitors.persistence.enumeration.Title;
import ch.elca.visitors.persistence.nativesql.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@NamedNativeQuery(name = "Visitor.search", query = Query.SEARCH_VISITOR_ORGANISER_SQL, resultSetMapping = "searchMapping")
@SqlResultSetMapping(name = "searchMapping", classes = {
        @ConstructorResult(targetClass = SearchResultItem.class, columns = {
                @ColumnResult(name = "first_name", type = String.class),
                @ColumnResult(name = "last_name", type = String.class),
                @ColumnResult(name = "status", type = Boolean.class),
                @ColumnResult(name = "checked_in_time", type = LocalDateTime.class)})
})
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

    @Min(value = 10, message = "Badge number should not be less than 10")
    @Max(value = 10, message = "Badge number should not be greater than 10")
    @Column(name = "badge_number", nullable = false, length = 10)
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

//    @ManyToMany(cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE
//            })
//    @JoinTable(name ="visitor_organiser",
//            joinColumns =@JoinColumn(name ="visitor_id"),
//            inverseJoinColumns =@JoinColumn(name ="organiser_id"))
//    private List<Organiser> organisers;


//    @OneToMany
//    private List<Organiser> organiser;

}
