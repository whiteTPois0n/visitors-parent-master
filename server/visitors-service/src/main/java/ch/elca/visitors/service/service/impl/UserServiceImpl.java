package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.entity.User;
import ch.elca.visitors.persistence.repository.UserRepository;
import ch.elca.visitors.service.dto.LoginDto;
import ch.elca.visitors.service.dto.RegisterDto;
import ch.elca.visitors.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


//    @Override
//    public void authenticateUser(LoginDto loginDto) {
//
//    }

    @Override
    public void RegisterUser(RegisterDto registerDto) {
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(encodedPassword);
//        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEnabled(true);
        user.setRole(registerDto.getRole());
        userRepository.save(user);
    }

}
