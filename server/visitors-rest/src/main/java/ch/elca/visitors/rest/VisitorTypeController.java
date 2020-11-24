package ch.elca.visitors.rest;

import ch.elca.visitors.service.dto.VisitorTypeDto;
import ch.elca.visitors.service.service.VisitorTypeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/visitor-type")
//@CrossOrigin()
public class VisitorTypeController {


    private final VisitorTypeService visitorTypeService;


    @PostMapping("/add")
    public String addVisitorType(@RequestBody VisitorTypeDto visitorTypeDto) {
        return visitorTypeService.addVisitorType(visitorTypeDto);
    }
}
