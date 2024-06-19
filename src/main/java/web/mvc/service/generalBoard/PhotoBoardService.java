package web.mvc.service.generalBoard;

import org.springframework.core.io.Resource;
import web.mvc.dto.generalBoard.PhotoBoardDTO;

import java.util.List;

public interface PhotoBoardService {
    List<PhotoBoardDTO> getAllPhotoBoards();

//    PhotoBoardDTO createPhotoBoard(PhotoBoardDTO photoBoardDTO);

//    PhotoBoardDTO getPhotoBoardById(Long photoBoardSeq);
//
//    PhotoBoardDTO updatePhotoBoard(PhotoBoardDTO photoBoardDTO);
//
//    String deletePhotoBoard(Long photoBoardSeq);

    Resource getPhotoBoardMainImg(String photoSrc);

    Resource getPhotoBoardDetailImg(String photoSrc);


}
