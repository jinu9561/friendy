package web.mvc.service.meetUpBoard;


import web.mvc.dto.meetUpBoard.MeetUpBoardDTO;
import web.mvc.entity.meetUpBoard.MeetUpBoard;


import java.util.Date;
import java.util.List;


public interface MeetUpBoardService {


    String createParty(MeetUpBoardDTO partyBoardDTO);

    String updateBoard(MeetUpBoardDTO partyBoardDTO);

    String deleteBoard(Long partySeq);

    List<MeetUpBoard> selectAll();

    List<Date> findByPartySeq();

    void checkDeadLine();

}
