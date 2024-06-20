package web.mvc.service.generalBoard;

import org.springframework.core.io.Resource;
import web.mvc.dto.generalBoard.PhotoBoardDTO;
import web.mvc.dto.generalBoard.DetailImagesDTO;
import web.mvc.dto.generalBoard.PhotoBoardWithDetailDTO;

import java.util.List;

public interface PhotoBoardService {

    //모든 사진게시물을 가져오는 메서드
    List<PhotoBoardDTO> getAllPhotoBoards();

    //사진 게시물을 생성하는 메서드
    PhotoBoardDTO createPhotoBoard(PhotoBoardDTO photoBoardDTO, List<DetailImagesDTO> detailImagesDTOS);

    //특정 ID(photoBoardSeq)를 가진 사진 게시물을 조회
    PhotoBoardWithDetailDTO getPhotoBoardById(Long photoBoardSeq);

    //특정 ID(photoBoardSeq)를 가진 사진 게시물을 수정
    PhotoBoardDTO updatePhotoBoard(Long id, PhotoBoardDTO photoBoardDTO, List<DetailImagesDTO> detailImagesDTOS);

    //특정 ID(photoBoardSeq)를 가진 사진 게시물을 삭제
    String deletePhotoBoard(Long photoBoardSeq);

    //메인 이미지 리소스로 불러오기
    Resource getMainImg(String imgName);

    //상세 이미지 리소스로 불러오기
    Resource getDetailImg(String imgName);


}
