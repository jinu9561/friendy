package web.mvc.service.friend;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import web.mvc.entity.friend.FriendList;
import web.mvc.entity.friend.FriendRequest;
import web.mvc.entity.user.Users;
import web.mvc.repository.friend.FriendListRepository;
import web.mvc.repository.friend.FriendRequestRepository;
import web.mvc.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Commit
@Slf4j
public class FriendServiceTest {

    @Autowired
    FriendListRepository friendListRepository;
    @Autowired
    FriendRequestRepository friendRequestRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendService friendService;

    private Users sender;
    private Users receiver;

    @BeforeEach
    void setUp() {
        // Test Users 생성
        sender = new Users();
        sender.setUserId("sender123");
        sender.setUserName("Sender Name");
        sender.setEmail("sender@example.com");
        userRepository.save(sender);

        receiver = new Users();
        receiver.setUserId("receiver123");
        receiver.setUserName("Receiver Name");
        receiver.setEmail("receiver@example.com");
        userRepository.save(receiver);
    }

    @Test
    void testSendFriendRequest() {
        FriendRequest friendRequest = friendService.sendFriendRequest(sender, receiver);
        assertThat(friendRequest).isNotNull();
        assertThat(friendRequest.getSender()).isEqualTo(sender);
        assertThat(friendRequest.getReceiver()).isEqualTo(receiver);
        assertThat(friendRequest.getRequestStatus()).isEqualTo(0);
    }

    @Test
    void testAcceptFriendRequest() {
        FriendRequest friendRequest = friendService.sendFriendRequest(sender, receiver);
        friendService.acceptFriendRequest(friendRequest.getFriendRequestSeq());

        Optional<FriendList> friendship = friendListRepository.findFriendship(sender, receiver);
        assertThat(friendship).isPresent();
        assertThat(friendship.get().getFriendStatus()).isEqualTo(0);
    }

    @Test
    void testRejectFriendRequest() {
        FriendRequest friendRequest = friendService.sendFriendRequest(sender, receiver);
        friendService.rejectFriendRequest(friendRequest.getFriendRequestSeq());

        Optional<FriendRequest> rejectedRequest = friendRequestRepository.findById(friendRequest.getFriendRequestSeq());
        assertThat(rejectedRequest).isPresent();
        assertThat(rejectedRequest.get().getRequestStatus()).isEqualTo(2);
    }

    @Test
    void testDeleteFriend() {
        FriendRequest friendRequest = friendService.sendFriendRequest(sender, receiver);
        friendService.acceptFriendRequest(friendRequest.getFriendRequestSeq());

        friendService.deleteFriend(sender, receiver);

        Optional<FriendList> friendship = friendListRepository.findFriendship(sender, receiver);
        assertThat(friendship).isNotPresent();
    }

    @Test
    void testBlockFriend() {
        FriendRequest friendRequest = friendService.sendFriendRequest(sender, receiver);
        friendService.acceptFriendRequest(friendRequest.getFriendRequestSeq());

        friendService.blockFriend(sender, receiver);

        Optional<FriendList> blockedFriendship = friendListRepository.findFriendship(sender, receiver);
        assertThat(blockedFriendship).isPresent();
        assertThat(blockedFriendship.get().getFriendStatus()).isEqualTo(1);
    }

    @Test
    void testGetAllFriends() {
        FriendRequest friendRequest = friendService.sendFriendRequest(sender, receiver);
        friendService.acceptFriendRequest(friendRequest.getFriendRequestSeq());

        List<FriendList> friends = friendService.getAllFriends(sender);
        assertThat(friends).isNotEmpty();
        assertThat(friends.get(0).getFriendUser()).isEqualTo(receiver);
    }
}

