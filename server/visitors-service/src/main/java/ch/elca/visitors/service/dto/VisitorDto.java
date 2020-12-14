package ch.elca.visitors.service.dto;

import ch.elca.visitors.persistence.enumeration.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VisitorDto {

    private Long id;

    private Title title;

    private String firstName;

    private String lastName;

    private String email;

    private Integer phoneNumber;

    private VisitorTypeDto visitorType;

    private String organization;

    private String badgeNumber;

    private String contactPerson;

    private String reasonOfVisit;

    private Double temperature;

    private String address;

    private LocalDateTime checkedIn;

//        private LocalDateTime checkedOut;

    private Boolean status;

}
