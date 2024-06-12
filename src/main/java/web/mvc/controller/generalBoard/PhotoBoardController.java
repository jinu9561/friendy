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

    @PostMapping("/photos")
    public ResponseEntity<PhotoBoardDTO> createPhotoBoard(@RequestBody PhotoBoardDTO photoBoardDTO) {
        return ResponseEntity.ok(photoBoardService.createPhotoBoard(photoBoardDTO));
    }

    @GetMapping("/photos")
    public ResponseEntity<List<PhotoBoardDTO>> getAllPhotoBoards() {
        return ResponseEntity.ok(photoBoardService.getAllPhotoBoards());
    }

//    @GetMapping("/{photoBoardSeq}")
//    public ResponseEntity<PhotoBoardDTO> getPhotoBoardById(@PathVariable Long photoBoardSeq) {
//        return ResponseEntity.ok(photoBoardService.getPhotoBoardById(photoBoardSeq));
//    }
//
//    @PutMapping("/{photoBoardSeq}")
//    public ResponseEntity<PhotoBoardDTO> updatePhotoBoard(@PathVariable Long photoBoardSeq, @RequestBody PhotoBoardDTO photoBoardDTO) {
//        return ResponseEntity.ok(photoBoardService.updatePhotoBoard(photoBoardSeq, photoBoardDTO));
//    }
//
//    @DeleteMapping("/{photoBoardSeq}")
//    public ResponseEntity<Void> deletePhotoBoard(@PathVariable Long photoBoardSeq) {
//        photoBoardService.deletePhotoBoard(photoBoardSeq);
//        return ResponseEntity.noContent().build();
//    }
}
