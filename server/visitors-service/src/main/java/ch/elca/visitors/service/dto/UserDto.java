package ch.elca.visitors.service.dto;

import ch.elca.visitors.persistence.entity.Role;
import ch.elca.visitors.persistence.enumeration.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private RoleEnum role;

}
