package SimbirSoftProject.controller;

import SimbirSoftProject.dto.ParticipantsDto;
import SimbirSoftProject.exceptions.ResourceNotFoundException;
import SimbirSoftProject.service.interfaces.ParticipantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        if (participantsDto == null){
            throw new ResourceNotFoundException("Participant was not added, check input data", "participant room id ,participant user id");
        }
        this.participantsService.addParticipant(participantsDto);
        return new ResponseEntity<>(participantsDto,HttpStatus.OK);
    }
    @GetMapping("/get/all")
    public ResponseEntity<List<ParticipantsDto>> getAll(){
        List<ParticipantsDto> participantsDtoList = this.participantsService.getAll();

        if(participantsDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(participantsDtoList,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ParticipantsDto> deleteParticipant(@PathVariable Long id){
        ParticipantsDto participantsDto = this.participantsService.getParticipantById(id);
        if(participantsDto == null){
            throw new ResourceNotFoundException("Participant with id " + id + "not found", "participant id");
        }
        this.participantsService.deleteParticipantById(id);
        return new ResponseEntity<>(participantsDto,HttpStatus.NO_CONTENT);

    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ParticipantsDto> getParticipantById(@PathVariable Long id){
        ParticipantsDto participantsDto = this.participantsService.getParticipantById(id);
        if (participantsDto == null){
            throw new ResourceNotFoundException("Participant with id " + id + "not found", "participant id");
        }
        return ResponseEntity.ok(participantsDto);
    }
}
