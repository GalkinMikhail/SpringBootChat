package SimbirSoftProject.controller;

import SimbirSoftProject.controller.dto.ParticipantsDto;
import SimbirSoftProject.entity.Participants;
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.participantsService.addParticipant(participantsDto);
        return new ResponseEntity<>(participantsDto,HttpStatus.OK);
    }
    @GetMapping("/get/all")
    public ResponseEntity<List<ParticipantsDto>> getAll(){
        List<ParticipantsDto> participantsDtoList = this.participantsService.getAll();

        if(participantsDtoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(participantsDtoList,HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ParticipantsDto> deleteParticipant(@PathVariable Long id){
        ParticipantsDto participantsDto = this.participantsService.getParticipantById(id);
        if(participantsDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.participantsService.deleteParticipantById(id);
        return new ResponseEntity<>(participantsDto,HttpStatus.NO_CONTENT);

    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ParticipantsDto> getParticipantById(@PathVariable Long id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ParticipantsDto participantsDto = this.participantsService.getParticipantById(id);
        if (participantsDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(participantsDto);
    }
}
