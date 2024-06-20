package web.mvc.repository.generalBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.entity.generalBoard.DetailImages;
import web.mvc.entity.generalBoard.PhotoBoard;

public interface PhotoBoardDetailImgRepository extends JpaRepository<DetailImages, Long> {
    void deleteByPhotoBoard(PhotoBoard photoBoard);
}
