package ch.elca.visitors.rest.controller;

import ch.elca.visitors.service.dto.ContactDto;
import ch.elca.visitors.service.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
@CrossOrigin()
public class ContactController {

    private final ContactService contactService;


    @GetMapping("/search/all")
    public List<ContactDto> getAllContacts() {
        return contactService.getAllContacts();
    }

}
