package web.mvc.service.generalBoard;

import web.mvc.dto.generalBoard.PhotoBoardDTO;

import java.util.List;

public interface PhotoBoardService {
    PhotoBoardDTO createPhotoBoard(PhotoBoardDTO photoBoardDTO);
    PhotoBoardDTO getPhotoBoardById(Long photoBoardSeq);
    List<PhotoBoardDTO> getAllPhotoBoards();
    PhotoBoardDTO updatePhotoBoard(Long photoBoardSeq, PhotoBoardDTO photoBoardDTO);
    void deletePhotoBoard(Long photoBoardSeq);
}
