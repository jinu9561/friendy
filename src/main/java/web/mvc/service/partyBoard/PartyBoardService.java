package web.mvc.service.partyBoard;


import web.mvc.dto.partyBoard.PartyBoardDTO;
import web.mvc.entity.partyBoard.PartyBoard;
import web.mvc.repository.partyRoom.PartyBoardRepository;

import java.util.List;


public interface PartyBoardService {


    String createParty(PartyBoardDTO partyBoardDTO);

    String updateBoard(PartyBoardDTO partyBoardDTO);

    String deleteBoard(Long partySeq);

    List<PartyBoard> selectAll();

    String findByPartySeq();

    void checkDeadLine();

}
