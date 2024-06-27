package web.mvc.controller.meetUpBoard;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.meetUpBoard.MeetUpRequestDTO;
import web.mvc.dto.user.UsersDTO;
import web.mvc.entity.friend.FriendRequest;
import web.mvc.entity.meetUpBoard.MeetUpRequest;
import web.mvc.entity.user.Users;
import web.mvc.repository.user.UserRepository;
import web.mvc.service.meetUpBoard.MeetUpRequestService;

import java.util.*;

@RestController
@RequestMapping("/partyBoard/")
@AllArgsConstructor
public class MeetUpRequestController {

    private final MeetUpRequestService meetUpRequestService;
    private final UserRepository userRepository;

    @PostMapping(value = "/request", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> meetUpRequest(@ModelAttribute MeetUpRequestDTO meetUpRequestDTO) {

        // 신청 중복 확인
        List<Long> list = meetUpRequestService.checkValidRequest(meetUpRequestDTO.getMeetUpSeq());
        for (Long seq : list) {
            // 이 부분에서 에러를 발생시킵니다.
            if (seq == meetUpRequestDTO.getUserSeq()) {
                // 에러 메시지를 Map에 저장하여 반환
                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("message", "이미 신청하셨습니다. 신청을 기다려주세요.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
            }
        }
        String result = meetUpRequestService.createMeetUpRequest(meetUpRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    //신청창에 신청 리스트 출력.
    //입력받은 소모임 시퀀스로
    @GetMapping("/request/selectBySeq")
    public ResponseEntity<?> meetUpRequestCheck(@RequestParam Long meetUpSeq) {
        List<MeetUpRequest> list = meetUpRequestService.findAllReqestBySeq(meetUpSeq);
        List<MeetUpRequestDTO> requestList = new ArrayList<>();


        for (MeetUpRequest meetUpRequest : list) {
            MeetUpRequestDTO meetUpRequestDTO = MeetUpRequestDTO.builder()
                    .userNickName(meetUpRequest.getUser().getNickName())
                    .userSeq(meetUpRequest.getUser().getUserSeq())
                    .meetUpRequestStatus(meetUpRequest.getMeetUpRequestStatus())
                    .meetUpSeq(meetUpRequest.getMeetUpRequestSeq())
                    .meetUpRequestSeq(meetUpRequest.getMeetUpRequestSeq())
                    .requestText(meetUpRequest.getRequestText())
                    .build();
            requestList.add(meetUpRequestDTO);
        }


        return ResponseEntity.status(HttpStatus.OK).body(requestList);
    }


    @PutMapping(value = "/request/changestatus")

    public ResponseEntity<?> changeStatus(@RequestBody MeetUpRequestDTO meetUpRequestDTO) {

        int status = meetUpRequestDTO.getMeetUpRequestStatus();
        Long meetUpSeq = meetUpRequestDTO.getMeetUpSeq();

        Long userSeq = meetUpRequestDTO.getUserSeq();
        if (status == 1) {
            meetUpRequestService.addMeetUpPeopleList(meetUpRequestDTO.getUserSeq(), meetUpRequestDTO.getMeetUpSeq());


            String result = meetUpRequestService.updateStatusByReqSeq(status, meetUpSeq, userSeq);


        } else if (status == 2) {

            meetUpRequestService.deleteFromMeetUp(meetUpRequestDTO.getUserSeq(), meetUpRequestDTO.getMeetUpSeq());


            String result2 = meetUpRequestService.updateStatusByReqSeq(status, meetUpSeq, userSeq);
            return ResponseEntity.status(HttpStatus.OK).body("모임 참가가 거절되었습니다.");


        }


        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    @DeleteMapping(value = "/request/delete")
    public ResponseEntity<?> deleteFromMeetUp(@RequestBody MeetUpRequestDTO meetUpRequestDTO) {
        //삭제 하고자 하는 소모임의(소모임 시퀀스)  참여 명단에서 삭제당하는 유저의 시퀀스를 넘김.


        meetUpRequestService.deleteFromMeetUp(meetUpRequestDTO.getMeetUpSeq(), meetUpRequestDTO.getUserSeq());
        return null;
    }

    @GetMapping(value = "/request/profile/{userSeq}")
    public ResponseEntity<?> selectProfileBySeq(@PathVariable Long userSeq) {


        System.out.println("userSeq: " + userSeq);
        Optional<Users> usersOptional = userRepository.findById(userSeq);
        UsersDTO usersDTO = new UsersDTO();
        if (usersOptional.isPresent()) {
            Users users = usersOptional.get();
            users.getNickName();
            usersDTO = UsersDTO.builder()
                    .nickName(users.getNickName())
                    .email(users.getEmail())
                    .country(users.getCountry())
                    .gender(users.getGender())
                    .phone(users.getPhone())
                    .build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(usersDTO);

    }


}