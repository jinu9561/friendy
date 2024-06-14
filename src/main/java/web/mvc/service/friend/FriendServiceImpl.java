package web.mvc.service.friend;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import web.mvc.entity.friend.FriendList;
import web.mvc.entity.friend.FriendRequest;
import web.mvc.entity.user.Users;
import web.mvc.repository.friend.FriendListRepository;
import web.mvc.repository.friend.FriendRequestRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FriendServiceImpl implements FriendService {

    private final FriendRequestRepository friendRequestRepository;
    private final FriendListRepository friendListRepository;

    @Override
    public FriendRequest sendFriendRequest(Users sender, Users receiver) {
        Optional<FriendRequest> existingRequest = friendRequestRepository.findFriendRequest(sender, receiver);
        if (existingRequest.isPresent()) {
            throw new IllegalStateException("이미 친구 요청을 보냈거나 수락 대기 중입니다.");
        }
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setRequestStatus(0); // 대기 상태
        return friendRequestRepository.save(friendRequest);
    }

    @Override
    public void acceptFriendRequest(Long friendRequestSeq) {
        friendRequestRepository.acceptFriendRequest(friendRequestSeq);
        Optional<FriendRequest> friendRequestOpt = friendRequestRepository.findById(friendRequestSeq);
        if (friendRequestOpt.isPresent()) {
            FriendRequest friendRequest = friendRequestOpt.get();
            Users sender = friendRequest.getSender();
            Users receiver = friendRequest.getReceiver();

            // 친구 관계 저장
            FriendList friendList = new FriendList();
            friendList.setUser(sender);
            friendList.setFriendUser(receiver);
            friendList.setFriendStatus(0); // 친구 상태

            friendListRepository.save(friendList);
        } else {
            throw new IllegalStateException("친구 요청을 찾을 수 없습니다.");
        }
    }

    @Override
    public void rejectFriendRequest(Long friendRequestSeq) {
        friendRequestRepository.rejectFriendRequest(friendRequestSeq);
    }

    @Override
    public void deleteFriend(Users user, Users friendUser) {
        friendListRepository.deleteFriendship(user, friendUser);
    }

    @Override
    public void blockFriend(Users user, Users friendUser) {
        friendListRepository.blockFriend(user, friendUser);
    }

    @Override
    public List<FriendList> getAllFriends(Users user) {
        return friendListRepository.findAllFriends(user);
    }
}
