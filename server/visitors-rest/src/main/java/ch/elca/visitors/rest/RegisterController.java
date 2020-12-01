package ch.elca.visitors.rest;

import ch.elca.visitors.service.dto.RegisterDto;
import ch.elca.visitors.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/super-admin")
@CrossOrigin()
public class RegisterController {

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        userService.RegisterUser(registerDto);
        return new ResponseEntity<>("Registration successful", HttpStatus.OK);
    }

}
