package web.mvc.controller.generalBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.generalBoard.PhotoBoardDTO;
import web.mvc.dto.generalBoard.DetailImagesDTO;
import web.mvc.dto.generalBoard.PhotoBoardRequestDTO;
import web.mvc.dto.generalBoard.PhotoBoardWithDetailDTO;
import web.mvc.service.generalBoard.PhotoBoardService;

import java.util.List;

@RestController
@RequestMapping("photo-board")
public class PhotoBoardController {

    @Autowired
    private PhotoBoardService photoBoardService;

    /*모든 사진 게시물을 조회*/
    @GetMapping("/")
    public ResponseEntity<List<PhotoBoardDTO>> getAllPhotoBoards() {
        return ResponseEntity.ok(photoBoardService.getAllPhotoBoards());
    }


    /*사진 게시물을 생성*/
    @PostMapping("/")
    public ResponseEntity<PhotoBoardDTO> createPhotoBoard(@RequestBody PhotoBoardRequestDTO request) {
        PhotoBoardDTO photoBoardDTO = request.getPhotoBoardDTO();
        List<DetailImagesDTO> detailImagesDTOS = request.getDetailImagesDTOS();
        PhotoBoardDTO createdPhotoBoard = photoBoardService.createPhotoBoard(photoBoardDTO, detailImagesDTOS);
        return ResponseEntity.ok(createdPhotoBoard);
    }

    /*특정 ID(photoBoardSeq)를 가진 사진 게시물을 조회*/
    @GetMapping("/{photoBoardSeq}")
    public ResponseEntity<PhotoBoardWithDetailDTO> getPhotoBoardById(@PathVariable Long photoBoardSeq) {
        return ResponseEntity.ok(photoBoardService.getPhotoBoardById(photoBoardSeq));
    }

    /*특정 ID(photoBoardSeq)를 가진 사진 게시물을 수정*/
    @PutMapping("/{photoBoardSeq}")
    public ResponseEntity<PhotoBoardDTO> updatePhotoBoard(@PathVariable Long photoBoardSeq, @RequestBody PhotoBoardRequestDTO request) {
        PhotoBoardDTO photoBoardDTO = request.getPhotoBoardDTO();
        List<DetailImagesDTO> detailImagesDTOS = request.getDetailImagesDTOS();
        PhotoBoardDTO updatedPhotoBoard = photoBoardService.updatePhotoBoard(photoBoardSeq, photoBoardDTO, detailImagesDTOS);
        return ResponseEntity.ok(updatedPhotoBoard);
    }

    /*특정 ID(photoBoardSeq)를 가진 사진 게시물을 삭제*/
    @DeleteMapping("/{photoBoardSeq}")
    public ResponseEntity<String> deletePhotoBoard(@PathVariable Long photoBoardSeq) {
        return ResponseEntity.ok(photoBoardService.deletePhotoBoard(photoBoardSeq));
    }

    //메인 이미지 불러오기
    @GetMapping("/main/img")
    public ResponseEntity<?> getMainImg(@RequestParam String imgName) {
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardService.getMainImg(imgName));
    }

    //상세 이미지 불러오기
    @GetMapping("/detail/img")
    public ResponseEntity<?> getDetailImg(@RequestParam String imgName) {
        return ResponseEntity.status(HttpStatus.OK).body(photoBoardService.getDetailImg(imgName));
    }
}
