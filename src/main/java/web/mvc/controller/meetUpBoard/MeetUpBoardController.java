package web.mvc.controller.meetUpBoard;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.meetUpBoard.MeetUpBoardDTO;
import web.mvc.dto.meetUpBoard.MeetUpDeleteDTO;
import web.mvc.dto.meetUpBoard.MeetUpUpdateDTO;
import web.mvc.entity.meetUpBoard.MeetUpBoard;
import web.mvc.entity.user.Interest;
import web.mvc.repository.user.InterestRepository;
import web.mvc.service.meetUpBoard.MeetUpBoardService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partyBoard")
@Slf4j
public class MeetUpBoardController {

    private final MeetUpBoardService meetUpBoardService;
    private final InterestRepository interestRepository;

    @PostMapping(value = "/create", produces = "application/json; charset=UTF-8")
    //게시글 생성
    public ResponseEntity<?> createPartyBoard(@RequestBody MeetUpBoardDTO meetUpBoardDTO) throws Exception{
        //입력된 타입이 안맞으면 @RequestBody에서 애초에 걸러져버림. .
        System.out.println("여기도 못가는거 맞지 ");
        System.out.println("받아온시퀀스"+meetUpBoardDTO.getUserSeq());

    String result =meetUpBoardService.createParty(meetUpBoardDTO);
        System.out.println(result+"결과");
        return ResponseEntity.status(HttpStatus.OK).body("성공");
    }

    @PutMapping("/update")
    //게시글 수정
    public ResponseEntity<?> updateMeetUpBoard(@RequestBody MeetUpUpdateDTO meetUpUpdateDTO) throws Exception {

            meetUpBoardService.updateBoard(meetUpUpdateDTO);
        return  ResponseEntity.status(HttpStatus.OK).body("test");
    }

    @GetMapping("/search/{meetUpName}")
    //게시글 이름으로 검색
    public ResponseEntity<?> selectMeetUpBoard(@PathVariable String meetUpName) {
        System.out.println("들어온 제목"+meetUpName);
        List<MeetUpBoard> list = meetUpBoardService.findByMeetUpName(meetUpName);
        System.out.println("제목 리스트" + list);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

//    @PutMapping("/invite")
//    public ResponseEntity<?> inviteMeetUpBoard(@)

    @GetMapping("/interestList")
    public ResponseEntity<?> findAllInterestList(){
        List<Interest> interestList=interestRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(interestList);
    }

    @GetMapping("/selectAll")
    public ResponseEntity<?> findAllMeetUp(){
        List<MeetUpBoard> meetUpBoardList= meetUpBoardService.selectAll();

        for (MeetUpBoard board : meetUpBoardList) {
            // 각 MeetUpBoard 객체의 정보를 출력합니다.
            System.out.println(board.getMeetUpName());

            // 필요에 따라 특정 속성에 접근하여 사용할 수 있습니다.
            // 예시: System.out.println(board.getSomeProperty());
        }
        System.out.println("meetUpBoaadList"+meetUpBoardList);

        return ResponseEntity.status(HttpStatus.OK).body(meetUpBoardList);
    }



    @GetMapping("/select/{meetUpName}")
    //게시글 상세보기
    public ResponseEntity<?> selectMeetUpSeq(@PathVariable String meetUpName){

        System.out.println("입력된 meetUpname"+ meetUpName);
        MeetUpBoard meet =meetUpBoardService.findMeetUpByMeetUpName(meetUpName);
        System.out.println("결과물"+meet);
       return ResponseEntity.status(HttpStatus.OK).body(meet);
    }

    @GetMapping("/select/{interest}")
    public ResponseEntity<?>  selectAllByInterest(@PathVariable Long interest){

        System.out.println(interest+"들어온인터레스트");
        Optional<Interest> interestOptional = interestRepository.findById(interest);
        System.out.println("이게 null 이니?"+interestOptional);
        if(interestOptional.isPresent()){

            Interest forInterest= interestOptional.get();
            String interestCate= forInterest.getInterestCategory();
            List<MeetUpBoard> meet=meetUpBoardService.findMeetUpByInterest(interestCate);
            return ResponseEntity.status(HttpStatus.OK).body(meet);

        }




        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }






    @DeleteMapping("/delete")
    //게시글 삭제
    public ResponseEntity<?> deleteMeetUPBoard(@RequestBody MeetUpDeleteDTO meetUpDeleteDTO) {
        String result = meetUpBoardService.deleteBoard(meetUpDeleteDTO);

        if (result == null) {
            // 삭제 성공 시
            return ResponseEntity.status(HttpStatus.OK).body("삭제가 성공적으로 완료되었습니다.");
        } else {
            // 삭제 실패 시
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
    }
}
