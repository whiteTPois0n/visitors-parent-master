package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.RegisterDto;
import ch.elca.visitors.service.dto.UserDto;

public interface UserService {

    void RegisterUser(RegisterDto registerDto);

    UserDto getUserRole(String username);

    String login();

}