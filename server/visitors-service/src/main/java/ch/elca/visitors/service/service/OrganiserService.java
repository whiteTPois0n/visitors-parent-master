package ch.elca.visitors.service.service;

import ch.elca.visitors.service.dto.OrganiserDto;

import java.util.List;

public interface OrganiserService {

    OrganiserDto addOrganiser(OrganiserDto organiserDto);

    List<OrganiserDto> getAllOrganisers();

    OrganiserDto findOrganiser(Long id);

    OrganiserDto updateOrganiser(OrganiserDto organiserDto);

    void deleteOrganiser(Long id);

}
