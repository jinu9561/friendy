package web.mvc.controller.generalBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.generalBoard.CommunityBoardDTO;
import web.mvc.service.generalBoard.CommunityBoardService;

import java.util.List;

@RestController
@RequestMapping("/community-boards")
public class CommunityBoardController {

    @Autowired
    private CommunityBoardService communityBoardService;

    @PostMapping
    public ResponseEntity<CommunityBoardDTO> createCommunityBoard(@RequestBody CommunityBoardDTO communityBoardDTO) {
        return ResponseEntity.ok(communityBoardService.createCommunityBoard(communityBoardDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunityBoardDTO> getCommunityBoardById(@PathVariable Long id) {
        return ResponseEntity.ok(communityBoardService.getCommunityBoardById(id));
    }

    @GetMapping
    public ResponseEntity<List<CommunityBoardDTO>> getAllCommunityBoards() {
        return ResponseEntity.ok(communityBoardService.getAllCommunityBoards());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommunityBoardDTO> updateCommunityBoard(@PathVariable Long id, @RequestBody CommunityBoardDTO communityBoardDTO) {
        return ResponseEntity.ok(communityBoardService.updateCommunityBoard(id, communityBoardDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommunityBoard(@PathVariable Long id) {
        communityBoardService.deleteCommunityBoard(id);
        return ResponseEntity.noContent().build();
    }
}
