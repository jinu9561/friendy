package web.mvc.repository.meetUpBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.meetUpBoard.MeetUpBoard;

import java.util.Date;
import java.util.List;

public interface MeetUpBoardRepository extends JpaRepository<MeetUpBoard, Long> {

    @Query("select pb.meetUpDeadLine FROM MeetUpBoard pb")
    List<Date> findAllPartDeadLine();

    @Query("update MeetUpBoard p set p.meetUpStatus = 1 where p.meetUpSeq= ?1 ")
    int updatePartyStatus(Long partySeq);

    @Query("select  p.meetUpSeq  from MeetUpBoard p where p.meetUpDeadLine = ?1")
    List<Long> findByPartySeqByDeadLine(Date partDeadLine);

}
