package web.mvc.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.event.EventDTO;
import web.mvc.dto.event.EventDetailImgDTO;
import web.mvc.service.admin.AdminEventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/event")
@Slf4j
public class AdminEventController {

    private final AdminEventService adminEventService;


    //등록
    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@ModelAttribute EventDTO eventDTO, @RequestParam("file") MultipartFile file) {
        //return ResponseEntity.status(HttpStatus.OK).body(eventService.createEvent(eventDTO));
        log.info("여기는 오나?");
        String result = adminEventService.createEvent(eventDTO,file);
        if ("이벤트 등록이 완료되었습니다.".equals(result)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    //세부사진 등록
    @PostMapping("/picture/insert")
    public ResponseEntity<?> uploadEventDetail(@RequestParam Long eventSeq ,@RequestParam("file") MultipartFile file){
        log.info("세부사진 등록 되는지 확인");
        return ResponseEntity.status(HttpStatus.OK).body(adminEventService.uploadEventDetail(eventSeq,file));
    }


    //수정
    @PutMapping("/update/{eventSeq}")
    public ResponseEntity<?> updateEvent(@PathVariable Long eventSeq, @ModelAttribute EventDTO eventDTO,
                                         @RequestParam("file") MultipartFile file){
        log.info("이벤트수정확인");

        return ResponseEntity.status(HttpStatus.OK).body(adminEventService.updateEvent(eventSeq, eventDTO, file));
    }


    //세부 사진 수정
    @PutMapping("/picture/update/{eventDetailImgSeq}")
    public ResponseEntity<?> updateEventDetail(@PathVariable Long eventDetailImgSeq, @RequestParam("file") MultipartFile file,
                                              @ModelAttribute EventDetailImgDTO eventDetailImgDTO){
        return ResponseEntity.status(HttpStatus.OK).body(adminEventService.updateEventDetail(eventDetailImgSeq,file,eventDetailImgDTO));
    }

    //삭제
    @DeleteMapping("/delete/{eventSeq}")
    public String deleteEvent(@PathVariable Long eventSeq){
        adminEventService.deleteEvent(eventSeq);
        log.info("이벤트삭제확인");
        return "삭제되었습니다";
    }

    //전체검색(데드라인 순으로)
    @GetMapping("/eventlistdead")
    public ResponseEntity<?> getAllEventAdminList(){
        List<EventDTO> events = adminEventService.selectAllByDeadlLine();
        return ResponseEntity.ok(events);
    }


}
