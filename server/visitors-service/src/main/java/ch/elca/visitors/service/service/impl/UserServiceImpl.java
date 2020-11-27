package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.repository.UserRepository;
import ch.elca.visitors.service.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

//    @Override
//    public void RegisterUser(UserDto userDto) {
//        //String encodedPassword = passwordEncoder.encode(userDto.getPassword());
//        User user = new User();
//        user.setUsername(userDto.getUsername());
//        //user.setPassword(encodedPassword);
//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        user.setEnabled(true);
//        user.setAuthorities("ROLE_USER");
//        userRepository.save(user);
//    }
}
