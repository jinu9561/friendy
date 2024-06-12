package web.mvc.repository.generalBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.entity.generalBoard.PhotoBoard;

public interface PhotoBoardRepository extends JpaRepository<PhotoBoard, Long> {
}
