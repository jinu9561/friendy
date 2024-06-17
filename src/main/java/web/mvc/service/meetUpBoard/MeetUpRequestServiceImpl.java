package web.mvc.service.meetUpBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.dto.meetUpBoard.MeetUpRequestDTO;
import web.mvc.entity.meetUpBoard.MeetUpBoard;
import web.mvc.entity.meetUpBoard.MeetUpRequest;
import web.mvc.entity.user.Users;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.meetUpBoard.MeetUpBoardRepository;
import web.mvc.repository.meetUpBoard.MeetUpRequestRepository;
import web.mvc.repository.user.UserRepository;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MeetUpRequestServiceImpl implements MeetUpRequestService {

    final MeetUpBoardService meetUpBoardService;
    final MeetUpRequestRepository meetUpRequestRepository;
    final MeetUpBoardRepository meetUpBoardRepository;
    final UserRepository userRepository;

    @Override
    public String createMeetUpRequest(MeetUpRequestDTO meetUpRequestDTO) {

        Long seq = meetUpRequestDTO.getMeetUpSeq();
        //시퀀스로 최대인원 값 가져오고 , 최대인원의 값하고 현재 참여된 인원의 수가 같으면
        // 에러 메세지 발생 -> 인원이 가득찼습니다.
        MeetUpBoard meetUpBoardInfo = meetUpBoardService.findMeetUpByBoardSeq(seq);
        int maxEntry = meetUpBoardInfo.getMeetUpMaxEntry();
        String nowEntryList = meetUpBoardInfo.getMeetUpPeopleList();
        JSONArray jsonArray = new JSONArray(nowEntryList);
        List<Object> list = jsonArray.toList();
        Users users= Users.builder()
                .userSeq(meetUpRequestDTO.getUserSeq())
                .build();
        int count = 0;
        for (Object obj : list) {
            System.out.println(obj);
            count++;
        }

        if (maxEntry == count) {
            throw new GlobalException(ErrorCode.MAX_ENTRY);
        } else if (maxEntry >= count) {
            System.out.println("여기?");
            MeetUpRequest meetUpRequest = MeetUpRequest.builder()
                    .meetUpBoard(meetUpBoardInfo)
                    .user(users).
                    build();

            meetUpRequestRepository.save(meetUpRequest);


        }
        return "소모임 신청이 완료되었습니다. 모임장의 심사를 기다려주세요.";


    }

    @Override
    public List<Long> checkValidRequest(Long meetUpSeq) {
        System.out.println(meetUpSeq + "서비스단 시퀀스");
        List<Long> list = meetUpRequestRepository.findUserSeqByMeetUpReqSeq(meetUpSeq);
        for (Long seq : list) {
            // 각 seq 값을 사용하는 로직 작성
            System.out.println(seq);
        }
        return list;

    }


    public List<MeetUpRequest> findAllReqestBySeq(Long meetUpBoardSeq) {

        List<MeetUpRequest> list = meetUpRequestRepository.findAllByMeetUpSeq(meetUpBoardSeq);

        return list;

    }

    //소모임 신청서 상태 변경
    public String updateStatusByReqSeq( int meetUpRequestStatus,Long meetUpSeq, Long userSeq) {
        System.out.println("유저시퀀스 서비스" + userSeq);
        System.out.println("소모임 시퀀스 : " + meetUpSeq);
        System.out.println("스테이터스 :"+ meetUpRequestStatus);

        int result = meetUpRequestRepository.changeStatusBySeq( meetUpRequestStatus  , meetUpSeq,userSeq);
        System.out.println(result);

        System.out.println(meetUpSeq + " || " + meetUpRequestStatus);


        return null;
    }

    @Override
    public String addMeetUpPeopleList(Long userSeq, Long meetUpSeq) {
        System.out.println("userSeq" + userSeq);
        Optional<Users> optionalUsers = userRepository.findById(userSeq);
        if (optionalUsers.isPresent()) {
            Users users = optionalUsers.get();
            // users 객체 사용
            System.out.println("유저 아이디" + users.getUserId());
            String addUserName = users.getUserName();
            MeetUpBoard meetUpBoard = meetUpBoardService.findMeetUpByBoardSeq(meetUpSeq);
            String meetUpList = meetUpBoard.getMeetUpPeopleList();
            JSONArray jsonArray = new JSONArray(meetUpList);
            List<Object> list = jsonArray.toList();
            System.out.println("리스트 출력" + list);
            list.add(addUserName);
            System.out.println("추가후 리스트 " + list);
            // String 으로 바꿔서 다시 업데이트 .
            JSONArray updatedJsonArray = new JSONArray(list);
            String updatedMeetUpList = updatedJsonArray.toString();
            int result = meetUpBoardRepository.addMeetUpPeopleList(updatedMeetUpList, meetUpSeq);
            System.out.println("업데이트결과" + result);
        }

        return null;
    }

    @Override
    public void deleteFromMeetUp( Long userSeq, Long meetUpSeq) {
        // 해당 소모임에서 삭제하기위한 소모임의 seq , 유저의 seq,
        MeetUpBoard meetUpBoard = meetUpBoardService.findMeetUpByBoardSeq(meetUpSeq);
        String meetUpList = meetUpBoard.getMeetUpPeopleList();
        JSONArray jsonArray = new JSONArray(meetUpList);
        List<Object> list = jsonArray.toList();

        Optional<Users> optionalUsers = userRepository.findById(userSeq);
        if (optionalUsers.isPresent()) {
            Users users = optionalUsers.get();

            for (Object obj : list) {
                System.out.println(obj);
                String userId=users.getUserId();
                System.out.println("삭제하고자 하는 유저 아이디"+userId);

                if(userId.equals(obj)){

                    System.out.println("삭제되었습니다"+obj);
                    list.remove(obj);
                    System.out.println("삭제후 "+list);
                    JSONArray updatedJsonArray = new JSONArray(list);
                    String updatedMeetUpList = updatedJsonArray.toString();
                    int result = meetUpBoardRepository.addMeetUpPeopleList(updatedMeetUpList, meetUpSeq);
                    System.out.println("삽입결과?"+result);
                    break;
                }
            }
        }

    }


}
