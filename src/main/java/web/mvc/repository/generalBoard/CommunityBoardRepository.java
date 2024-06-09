package web.mvc.repository.generalBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.entity.generalBoard.CommunityBoard;

public interface CommunityBoardRepository extends JpaRepository<CommunityBoard, Long> {
}
