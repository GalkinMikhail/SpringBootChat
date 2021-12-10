package SimbirSoftProject.controller;

import SimbirSoftProject.domain.util.Participants;
import SimbirSoftProject.service.interfaces.ParticipantsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantsController {

    private final ParticipantsService participantsService;

    public ParticipantsController(ParticipantsService participantsService){
        this.participantsService = participantsService;
    }
    @PostMapping("/add")
    public String addParticipants(@RequestBody Participants participants){
        participantsService.addParticipant(participants);
        return "Participant was added successfully";
    }
    @GetMapping("/get/all")
    public List<Participants> getAll(){
        return participantsService.getAll();
    }
    @DeleteMapping("/delete")
    public String deleteParticipant(@RequestBody Participants participants){
        participantsService.deleteParticipant(participants);
        return "Participant was deleted successfully";
    }
    @GetMapping("/get/{id}")
    public Participants getParticipantById(@PathVariable Long id){
        return participantsService.getParticipantById(id);
    }
}
