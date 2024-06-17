package web.mvc.repository.meetUpBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.meetUpBoard.MeetUpRequest;

import java.util.List;

public interface MeetUpRequestRepository extends JpaRepository<MeetUpRequest, Long> {

    @Query("select p from MeetUpRequest p where p.meetUpSeq = ?1")
    List<MeetUpRequest> findAllByMeetUpSeq(Long meetUpBoardSeq);

    @Modifying
    @Query("update MeetUpRequest p set p.meetUpRequestStatus =  ?1 where p.meetUpSeq= ?2 and  p.userSeq=?3")
    int changeStatusBySeq( int meetUpRequestStatus, Long meetUpSeq, Long userSeq);

    @Query("select  p.userSeq from MeetUpRequest p where  p.meetUpSeq=?1")
    List<Long> findUserSeqByMeetUpReqSeq(Long meetUpReqSeq);




}
