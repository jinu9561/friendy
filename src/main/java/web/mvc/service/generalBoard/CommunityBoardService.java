package web.mvc.service.generalBoard;

import web.mvc.dto.generalBoard.CommunityBoardDTO;

import java.util.List;

public interface CommunityBoardService {
    CommunityBoardDTO createCommunityBoard(CommunityBoardDTO communityBoardDTO);
    CommunityBoardDTO getCommunityBoardById(Long commBoardSeq);
    List<CommunityBoardDTO> getAllCommunityBoards();
    CommunityBoardDTO updateCommunityBoard(Long commBoardSeq, CommunityBoardDTO communityBoardDTO);
    void deleteCommunityBoard(Long commBoardSeq);
}
