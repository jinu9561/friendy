package web.mvc.controller.meetUpBoard;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.meetUpBoard.MeetUpRequestDTO;
import web.mvc.entity.meetUpBoard.MeetUpRequest;
import web.mvc.service.meetUpBoard.MeetUpRequestService;

import java.util.List;

@RestController
@RequestMapping("/partyBoard/")
@AllArgsConstructor
public class MeetUpRequestController {

    private final MeetUpRequestService meetUpRequestService;

    @PostMapping(value = "/request", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> meetUpRequest(@RequestBody MeetUpRequestDTO meetUpRequestDTO) {
        System.out.println("들어온 소모임 시퀀스" + meetUpRequestDTO.getMeetUpSeq());
        System.out.println("유저시퀀스" + meetUpRequestDTO.getUserSeq());

        // 신청 중복 확인
        List<Long> list = meetUpRequestService.checkValidRequest(meetUpRequestDTO.getMeetUpSeq());
        System.out.println("list" + list);
        for (Long seq : list) {
            System.out.println("시퀀스 검사" + seq);
            System.out.println("들어온 시퀀스" + meetUpRequestDTO.getUserSeq());

            // 이 부분에서 에러를 발생시킵니다.
            if (seq == meetUpRequestDTO.getUserSeq()) {
                throw new IllegalArgumentException("이미 신청하셨습니다. 신청을 기다려주세요.");
            }
        }

        String result = meetUpRequestService.createMeetUpRequest(meetUpRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    //신청창에 신청 리스트 출력.
    //입력받은 소모임 시퀀스로
    @GetMapping(value = "/request/{meetUpBoardSeq}")
    public ResponseEntity<?> meetUpRequestCheck(@PathVariable Long meetUpBoardSeq) {

        List<MeetUpRequest> list = meetUpRequestService.findAllReqestBySeq(meetUpBoardSeq);

        System.out.println("list : " + list);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


    @PutMapping(value = "/request/changestatus")

    public ResponseEntity<?> changeStatus(@RequestBody MeetUpRequestDTO meetUpRequestDTO) {

        int status = meetUpRequestDTO.getMeetUpRequestStatus();
        System.out.println("스테이터스" + status);
        Long meetUpSeq = meetUpRequestDTO.getMeetUpSeq();

        System.out.println("meetUpSeq" + meetUpSeq);
        Long userSeq = meetUpRequestDTO.getUserSeq();
        if (status == 1) {
            String result = meetUpRequestService.updateStatusByReqSeq( status ,meetUpSeq,userSeq );


            meetUpRequestService.addMeetUpPeopleList(meetUpRequestDTO.getUserSeq(), meetUpRequestDTO.getMeetUpSeq());

            System.out.println("status: " + status);

        } else if (status==2) {

            String result2 = meetUpRequestService.updateStatusByReqSeq( status ,meetUpSeq,userSeq );
            return ResponseEntity.status(HttpStatus.OK).body("모임 참가가 거절되었습니다.");


        }


        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    @DeleteMapping (value = "/request/delete")
    public ResponseEntity<?> deleteFromMeetUp(@RequestBody  MeetUpRequestDTO meetUpRequestDTO){
        //삭제 하고자 하는 소모임의(소모임 시퀀스)  참여 명단에서 삭제당하는 유저의 시퀀스를 넘김.


        meetUpRequestService.deleteFromMeetUp(meetUpRequestDTO.getMeetUpSeq(), meetUpRequestDTO.getUserSeq());
        return null;
    }



}