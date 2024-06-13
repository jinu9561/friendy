package web.mvc.controller.generalBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.generalBoard.PhotoBoardDTO;
import web.mvc.service.generalBoard.PhotoBoardService;

import java.util.List;

@RestController
@RequestMapping("photo-boards")
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
    public ResponseEntity<PhotoBoardDTO> createPhotoBoard(@RequestBody PhotoBoardDTO photoBoardDTO) {
        return ResponseEntity.ok(photoBoardService.createPhotoBoard(photoBoardDTO));
    }

    /*특정 ID(photoBoardSeq)를 가진 사진 게시물을 조회*/
    @GetMapping("/{photoBoardSeq}")
    public ResponseEntity<PhotoBoardDTO> getPhotoBoardById(@PathVariable Long photoBoardSeq) {
        return ResponseEntity.ok(photoBoardService.getPhotoBoardById(photoBoardSeq));
    }

    /*특정 ID(photoBoardSeq)를 가진 사진 게시물을 수정*/
    @PutMapping("/{photoBoardSeq}")
    public ResponseEntity<PhotoBoardDTO> updatePhotoBoard(@PathVariable Long photoBoardSeq, @RequestBody PhotoBoardDTO photoBoardDTO) {
        photoBoardDTO.setPhotoBoardSeq(photoBoardSeq); // URL 경로에서 받은 ID를 DTO에 설정
        return ResponseEntity.ok(photoBoardService.updatePhotoBoard(photoBoardDTO));
    }

    /*특정 ID(photoBoardSeq)를 가진 사진 게시물을 삭제*/
    @DeleteMapping("/{photoBoardSeq}")
    public String deletePhotoBoard(@PathVariable Long photoBoardSeq) {
        return photoBoardService.deletePhotoBoard(photoBoardSeq);
    }
}
