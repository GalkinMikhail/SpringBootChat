package SimbirSoftProject.controller;

import SimbirSoftProject.controller.dto.ParticipantsDto;
import SimbirSoftProject.entity.Participants;
import SimbirSoftProject.service.interfaces.ParticipantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participants")
@RequiredArgsConstructor
public class ParticipantsController {

    private final ParticipantsService participantsService;
    @PostMapping("/add")
    public ResponseEntity<ParticipantsDto> addParticipants(@RequestBody ParticipantsDto participantsDto){
        participantsService.addParticipant(participantsDto);
        return ResponseEntity.ok(participantsDto);
    }
    @GetMapping("/get/all")
    public List<Participants> getAll(){
        return participantsService.getAll();
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteParticipant(@PathVariable Long id){
        participantsService.deleteParticipantById(id);
        return ResponseEntity.ok("Participant was deleted successfully");
    }
    //@GetMapping("/get/{id}")
    //public Participants getParticipantById(@PathVariable Long id){
        //return participantsService.getParticipantById(id);
    //}
}
