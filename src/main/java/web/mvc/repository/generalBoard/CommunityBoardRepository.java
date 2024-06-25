package web.mvc.repository.generalBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.entity.generalBoard.CommunityBoard;

import java.util.List;

public interface CommunityBoardRepository extends JpaRepository<CommunityBoard, Long> {
    // boardType에 따라 커뮤니티 게시판을 조회
    List<CommunityBoard> findByBoardType(int boardType);
}
