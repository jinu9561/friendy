package web.mvc.service.meetUpBoard;

import web.mvc.dto.meetUpBoard.MeetUpRequestDTO;
import web.mvc.entity.meetUpBoard.MeetUpRequest;

import java.util.List;

public interface MeetUpRequestService {

    String createMeetUpRequest(MeetUpRequestDTO meetUpRequestDTO);

    List<Long> checkValidRequest(Long meetUpRequestSeq);


    List<MeetUpRequest> findAllReqestBySeq(Long meetUpBoardSeq);

     String updateStatusByReqSeq( int meetUpRequestStatus, Long meetUpSeq , Long userSeq);


     String addMeetUpPeopleList(  Long userSeq, Long meetUpSeq) ;


     void deleteFromMeetUp( Long userSeq, Long meetUpSeq);

    }
