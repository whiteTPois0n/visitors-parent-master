package ch.elca.visitors.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String visa;

}
