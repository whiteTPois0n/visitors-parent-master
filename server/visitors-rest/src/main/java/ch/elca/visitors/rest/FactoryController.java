package ch.elca.visitors.rest;

import ch.elca.visitors.service.dto.FactoryDto;
import ch.elca.visitors.service.service.FactoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/factory")
//@CrossOrigin()
public class FactoryController {

    private final FactoryService factoryService;


    @PostMapping("/create")
    public void addFactory(@RequestBody FactoryDto factoryDto) {
        factoryService.addFactory(factoryDto);
    }


    @GetMapping("/search/all")
    public List<FactoryDto> getAllFactories() {
        return factoryService.getAllFactories();
    }


    @GetMapping("/{id}")
    public FactoryDto findFactory(@PathVariable("id") Long id) {
        return factoryService.findFactory(id);
    }


    @PutMapping("/update")
    public void updateFactory(@RequestBody FactoryDto factoryDto) {
        factoryService.updateFactory(factoryDto);
    }
}
