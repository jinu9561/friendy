package web.mvc.repository.generalBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.entity.generalBoard.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByCommunityBoardCommBoardSeq(Long boardSeq);
}
