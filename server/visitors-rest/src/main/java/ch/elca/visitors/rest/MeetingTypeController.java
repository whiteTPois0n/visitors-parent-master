package ch.elca.visitors.rest;

import ch.elca.visitors.service.dto.MeetingTypeDto;
import ch.elca.visitors.service.service.MeetingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/meeting-type")
@CrossOrigin()
public class MeetingTypeController {

    private final MeetingTypeService meetingTypeService;


    @PostMapping("/create")
    public void addMeetingType(@RequestBody MeetingTypeDto meetingTypeDto) {
        meetingTypeService.addMeetingType(meetingTypeDto);
    }


    @GetMapping("/search/all")
    public List<MeetingTypeDto> getAllMeetingTypes() {
        return meetingTypeService.getAllMeetingTypes();
    }


    @GetMapping("/{id}")
    public MeetingTypeDto findMeetingType(@PathVariable("id") Long id) {
        return meetingTypeService.findMeetingType(id);
    }


    @PutMapping("/update")
    public void updateMeetingType(@RequestBody MeetingTypeDto meetingTypeDto) {
        meetingTypeService.updateMeetingType(meetingTypeDto);
    }
}
