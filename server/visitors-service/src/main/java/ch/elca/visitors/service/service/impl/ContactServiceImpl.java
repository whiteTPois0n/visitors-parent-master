package ch.elca.visitors.service.service.impl;

import ch.elca.visitors.persistence.repository.ContactRepository;
import ch.elca.visitors.service.dto.ContactDto;
import ch.elca.visitors.service.mapper.ContactMapper;
import ch.elca.visitors.service.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public List<ContactDto> getAllContacts() {
        var contacts = contactRepository.findAll();
        return contacts
                .stream()
                .map(contactMapper::mapToContactDto)
                .collect(Collectors.toList());
    }

}
