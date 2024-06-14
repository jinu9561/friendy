package web.mvc.controller.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.entity.friend.FriendList;
import web.mvc.entity.friend.FriendRequest;
import web.mvc.entity.user.Users;
import web.mvc.service.friend.FriendService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
@Slf4j
public class FriendController {

    private final FriendService friendService;

    /**
     * 친구 요청 보내기
     */
    @PostMapping("/request")
    public ResponseEntity<?> sendFriendRequest(@RequestParam Long receiverId, Principal principal) {
        Users sender = new Users(); // 현재 로그인한 사용자 정보를 Principal로부터 가져오기
        sender.setUserSeq(Long.parseLong(principal.getName()));
        Users receiver = new Users();
        receiver.setUserSeq(receiverId);
        try {
            FriendRequest friendRequest = friendService.sendFriendRequest(sender, receiver);
            return ResponseEntity.status(HttpStatus.OK).body(friendRequest);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * 친구 요청 수락
     */
    @PostMapping("/request/accept")
    public ResponseEntity<?> acceptFriendRequest(@RequestParam Long friendRequestSeq) {
        friendService.acceptFriendRequest(friendRequestSeq);
        return ResponseEntity.status(HttpStatus.OK).body("친구 요청이 수락되었습니다.");
    }

    /**
     * 친구 요청 거절
     */
    @PostMapping("/request/reject")
    public ResponseEntity<?> rejectFriendRequest(@RequestParam Long friendRequestSeq) {
        friendService.rejectFriendRequest(friendRequestSeq);
        return ResponseEntity.status(HttpStatus.OK).body("친구 요청이 거절되었습니다.");
    }

    /**
     * 친구 삭제
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFriend(@RequestParam Long friendUserId, Principal principal) {
        Users user = new Users(); // 현재 로그인한 사용자 정보를 Principal로부터 가져오기
        user.setUserSeq(Long.parseLong(principal.getName()));
        Users friendUser = new Users();
        friendUser.setUserSeq(friendUserId);
        friendService.deleteFriend(user, friendUser);
        return ResponseEntity.status(HttpStatus.OK).body("친구가 삭제되었습니다.");
    }

    /**
     * 친구 차단
     */
    @PostMapping("/block")
    public ResponseEntity<?> blockFriend(@RequestParam Long friendUserId, Principal principal) {
        Users user = new Users(); // 현재 로그인한 사용자 정보를 Principal로부터 가져오기
        user.setUserSeq(Long.parseLong(principal.getName()));
        Users friendUser = new Users();
        friendUser.setUserSeq(friendUserId);
        friendService.blockFriend(user, friendUser);
        return ResponseEntity.status(HttpStatus.OK).body("친구가 차단되었습니다.");
    }

    /**
     * 친구 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<List<FriendList>> getAllFriends(Principal principal) {
        Users user = new Users(); // 현재 로그인한 사용자 정보를 Principal로부터 가져오기
        user.setUserSeq(Long.parseLong(principal.getName()));
        List<FriendList> friends = friendService.getAllFriends(user);
        return ResponseEntity.status(HttpStatus.OK).body(friends);
    }
}
