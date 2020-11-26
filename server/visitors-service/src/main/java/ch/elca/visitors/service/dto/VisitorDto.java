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

    //        @Email
    private String email;

    private Integer phoneNumber;

    private VisitorTypeDto visitorType;

    private String organization;

    //    @Min(value = 20, message = "Badge number should not be less than 20")
//    @Max(value = 20, message = "Badge number should not be greater than 18")
    private String badgeNumber;

//    private Boolean status;

//    private LocalDateTime checkedOut;

    private String contactPerson;

    private String reasonOfVisit;

    private Double temperature;

    private String address;

    private LocalDateTime checkedIn;

    private Boolean status;

    public VisitorDto(String firstName, String lastName, Boolean status) {
    }
}
