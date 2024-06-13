package web.mvc.service.meetUpBoard;


import web.mvc.dto.meetUpBoard.MeetUpBoardDTO;
import web.mvc.dto.meetUpBoard.MeetUpDeleteDTO;
import web.mvc.dto.meetUpBoard.MeetUpUpdateDTO;
import web.mvc.entity.meetUpBoard.MeetUpBoard;


import java.util.Date;
import java.util.List;


public interface MeetUpBoardService {


    String createParty(MeetUpBoardDTO meetUpBoardDTO) throws  Exception;

    String updateBoard(MeetUpUpdateDTO meetUpUpdateDTO) throws  Exception;

    String deleteBoard(MeetUpDeleteDTO meetUpDeleteDTO);

    MeetUpBoard  findMeetUpByMeetUpName(String meetUpName);


    List<MeetUpBoard> findByMeetUpName(String meetUpName);
    List<MeetUpBoard> selectAll();

    List<Date> findByPartySeq();

    void checkDeadLine();

}
