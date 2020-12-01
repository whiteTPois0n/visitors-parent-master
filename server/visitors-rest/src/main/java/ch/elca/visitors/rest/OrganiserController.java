package ch.elca.visitors.rest;

import ch.elca.visitors.service.dto.OrganiserDto;
import ch.elca.visitors.service.service.OrganiserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/organiser")
//@CrossOrigin()
public class OrganiserController {

    private final OrganiserService organiserService;

    @PostMapping("/create")
    public OrganiserDto addOrganiser(@Valid @RequestBody OrganiserDto organiserDto) {
        return organiserService.addOrganiser(organiserDto);
    }

    @GetMapping("search/all")
    public List<OrganiserDto> getAllOrganisers() {
        return organiserService.getAllOrganisers();
    }


    @GetMapping("/{id}")
    public OrganiserDto findOrganiser(@PathVariable("id") Long id) {
        return organiserService.findOrganiser(id);
    }


    @PutMapping("/update")
    public OrganiserDto updateOrganiser(@RequestBody OrganiserDto organiserDto) {
        return organiserService.updateOrganiser(organiserDto);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteOrganiserById(@PathVariable Long id) {
        organiserService.deleteOrganiser(id);
    }

}
