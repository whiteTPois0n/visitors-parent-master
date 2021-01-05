package ch.elca.visitors.service.mapper;

import ch.elca.visitors.persistence.entity.User;
import ch.elca.visitors.service.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto mapToUserDto(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    User mapToUser(UserDto userDto);

}
