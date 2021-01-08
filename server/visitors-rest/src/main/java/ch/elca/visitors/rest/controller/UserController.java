package ch.elca.visitors.rest.controller;

import ch.elca.visitors.service.dto.RegisterDto;
import ch.elca.visitors.service.dto.UserDto;
import ch.elca.visitors.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin()
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        userService.RegisterUser(registerDto);
        return new ResponseEntity<>("Registration successful", HttpStatus.OK);
    }


    @GetMapping("/user-role/{username}")
    public UserDto getUserRole(@PathVariable("username") String username) {
        return userService.getUserRole(username);
    }


    @GetMapping("/login")
    public String login() {
        return userService.login();
    }


}