package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.LoginDto;
import ch.elca.visitors.service.dto.RegisterDto;

public interface UserService {

//    void authenticateUser(LoginDto loginDto);

    void RegisterUser(RegisterDto registerDto);

}
