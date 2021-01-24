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
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VISITOR")
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @Column(length = 100)
    private String address;

    @Column(length = 50)
    private String organization;

}
