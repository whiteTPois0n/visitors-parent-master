package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.entity.QUser;
import ch.elca.visitors.persistence.entity.User;
import ch.elca.visitors.persistence.repository.UserRepository;
import ch.elca.visitors.service.dto.RegisterDto;
import ch.elca.visitors.service.dto.UserDto;
import ch.elca.visitors.service.exception.ResourceNotFoundException;
import ch.elca.visitors.service.mapper.UserMapper;
import ch.elca.visitors.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public void RegisterUser(RegisterDto registerDto) {
        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        user.setRole(registerDto.getRole());
        userRepository.save(user);
    }


    @Override
    public UserDto getUserRole(String username) {
        var qUser = QUser.user;
        var userPredicate = qUser.username.eq(username);
        var user = userRepository.findOne(userPredicate)
                .orElseThrow(() -> new ResourceNotFoundException("User " + username + " Not Found"));
        return userMapper.mapToUserDto(user);
    }


    @Override
    public String login() {
        return "login successful";
    }

}
