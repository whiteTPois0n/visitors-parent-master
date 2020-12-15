package ch.elca.visitors.service.dto;

import ch.elca.visitors.persistence.enumeration.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String address;

    private String organization;

}
