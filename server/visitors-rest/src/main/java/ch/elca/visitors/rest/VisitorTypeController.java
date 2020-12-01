package ch.elca.visitors.rest;

import ch.elca.visitors.service.dto.VisitorTypeDto;
import ch.elca.visitors.service.service.VisitorTypeService;
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
@RequestMapping("/visitor-type")
//@CrossOrigin()
public class VisitorTypeController {

    private final VisitorTypeService visitorTypeService;


    @PostMapping("/create")
    public void addVisitorType(@RequestBody VisitorTypeDto visitorTypeDto) {
        visitorTypeService.addVisitorType(visitorTypeDto);
    }


    @GetMapping("/search/all")
    public List<VisitorTypeDto> getAllVisitorTypes() {
        return visitorTypeService.getAllVisitorTypes();
    }


    @GetMapping("/{id}")
    public VisitorTypeDto findVisitorType(@PathVariable("id") Long id) {
        return visitorTypeService.findVisitorType(id);
    }


    @PutMapping("/update")
    public void updateVisitorType(@RequestBody VisitorTypeDto visitorTypeDto) {
        visitorTypeService.updateVisitorType(visitorTypeDto);
    }

}
