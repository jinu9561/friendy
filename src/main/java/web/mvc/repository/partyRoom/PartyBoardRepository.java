package web.mvc.repository.partyRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.partyBoard.PartyBoard;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface PartyBoardRepository extends JpaRepository<PartyBoard , Long> {

    @Query("select pb.partDeadLine FROM PartyBoard pb")
    List<Date> findAllPartDeadLine();

    @Query("update PartyBoard p set p.partyStatus = 1 where p.partySeq= ?1 ")
    int updatePartyStatus(Long partySeq);

    @Query("select  p.partySeq  from PartyBoard p where p.partDeadLine = ?1")
    List<Long> findByPartySeqByDeadLine(Date partDeadLine);

}
