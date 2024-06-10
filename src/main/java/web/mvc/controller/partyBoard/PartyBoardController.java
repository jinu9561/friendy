package web.mvc.controller.partyBoard;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.partyBoard.PartyBoardDTO;
import web.mvc.service.partyBoard.PartyBoardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partyBoard")
@Slf4j
public class PartyBoardController {

    private final PartyBoardService partyBoardService;


    @PostMapping("/create")
    public ResponseEntity<?> createPartyBoard(@RequestBody PartyBoardDTO partyBoardDTO){
        //입력된 타입이 안맞으면 @RequestBody에서 애초에 걸러져버림. .

    String result =partyBoardService.createParty(partyBoardDTO);
        System.out.println(result+"결과");

        partyBoardService.findByPartySeq();

        return ResponseEntity.status(HttpStatus.OK).body("성공");
    }

    @GetMapping ("/test")
    public void test(){
        partyBoardService.findByPartySeq();
    }


}
