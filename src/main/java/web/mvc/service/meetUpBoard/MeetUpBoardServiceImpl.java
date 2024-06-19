package web.mvc.service.meetUpBoard;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.dto.chat.ChattingRoomDTO;
import web.mvc.dto.meetUpBoard.MeetUpBoardDTO;
import web.mvc.dto.meetUpBoard.MeetUpDeleteDTO;
import web.mvc.dto.meetUpBoard.MeetUpUpdateDTO;
import web.mvc.entity.chatting.ChattingRoom;
import web.mvc.entity.meetUpBoard.MeetUpBoard;
import web.mvc.entity.meetUpBoard.MeetUpBoardDetailImg;
import web.mvc.entity.user.Interest;
import web.mvc.entity.user.Users;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.meetUpBoard.MeetUpBoardDetailImgRepository;
import web.mvc.repository.meetUpBoard.MeetUpBoardRepository;
import web.mvc.repository.user.InterestRepository;
import web.mvc.repository.user.UserRepository;
import web.mvc.service.chatting.ChattingRoomService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MeetUpBoardServiceImpl implements MeetUpBoardService {

    private final MeetUpBoardRepository meetUpBoardRepository;
    private final MeetUpBoardDetailImgRepository meetUpBoardDetailImgRepository;
    private final ChattingRoomService chattingRoomService;
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;

    @Override
    public String createParty(MeetUpBoardDTO meetUpBoardDTO) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
        System.out.println("여기가맞냐 " + meetUpBoardDTO.getMeetUpDeadLine());
        Date date = formatter.parse(meetUpBoardDTO.getMeetUpDeadLine());
        MeetUpBoardDetailImg meetUpBoardDetailImg=null;
//        List<String> peopleList = new ObjectMapper().readValue(meetUpBoardDTO.getMeetUpPeopleList(), new TypeReference<List<String>>() {});
//        System.out.println(peopleList+"명단 이거 맞음 ?");

        List<String> list = meetUpBoardDTO.getMeetUpPeopleList();
        System.out.println("리스트" + list);
        ObjectMapper objectMapper = new ObjectMapper();
        String meetUpPeopleListJson;
        try {
            meetUpPeopleListJson = objectMapper.writeValueAsString(list);
            System.out.println("JSON: " + meetUpPeopleListJson);

        } catch (JsonProcessingException e) {
            // Handle JSON processing exception
            throw new GlobalException(ErrorCode.JSON_PROCESSING_ERROR);
        }


        try {
            date = formatter.parse(meetUpBoardDTO.getMeetUpDeadLine());
        } catch (ParseException e) {
            // meetUpDeadLine 날짜 형식이 잘못된 경우 처리
            throw new GlobalException(ErrorCode.WRONG_DATE);
        }
        System.out.println(date + "datedatedate");
        //---------------------------------------------
        Long interestSeq= meetUpBoardDTO.getInterestSeq();
        Optional<Interest> interestOptional=interestRepository.findById(interestSeq);
        System.out.println("여기인듯 ");
        System.out.println(interestOptional);
        if(interestOptional.isPresent()){
            Interest interest = interestOptional.get();
         String interestCategory=  interest.getInterestCategory();
            System.out.println("interestCategory"+interestCategory);
            Users users= Users.builder().
            userSeq(meetUpBoardDTO.getUserSeq()).build();

            MeetUpBoard meetUpBoard = MeetUpBoard.builder()
                    .user(users)
                    .meetUpName(meetUpBoardDTO.getMeetUpName())
                    .meetUpDesc(meetUpBoardDTO.getMeetUpDesc())
                    .meetUpDetailImgSeq(meetUpBoardDetailImg)
                    .interest(interestCategory)
                    .meetUpPwd(meetUpBoardDTO.getMeetUpPwd())
                    .meetUpDeadLine(date)
                    .meetUpMaxEntry(meetUpBoardDTO.getMeetUpMaxEntry())
                    .meetUpPeopleList(meetUpPeopleListJson)
                    .meetUpStatus(meetUpBoardDTO.getMeetUpStatus())
                    .build();
            System.out.println("그럼 여기까지 ?여기까지왔냐 ");
            try {
                meetUpBoardRepository.save(meetUpBoard);
                Optional<Users> optionalUsers = userRepository.findById(meetUpBoardDTO.getUserSeq());
                System.out.println("옵셔널?"+optionalUsers);
                if (optionalUsers.isPresent()) {
                    Users users2 = optionalUsers.get();
                    String userId= users2.getUserId();
                    ChattingRoomDTO chattingRoomDTO = ChattingRoomDTO.builder()
                            .userId(userId)
                            .build();
                    chattingRoomService.createChattingRoom(chattingRoomDTO);
                    System.out.println("채팅방생성파티보드");
                }
            } catch (NumberFormatException e) {
                System.out.println("에러메세지?" + e.getMessage());
                throw new GlobalException(ErrorCode.WRONG_TYPE);
            }
        }


            return "성공";


    }

    @Override
    public String updateBoard(MeetUpUpdateDTO meetUpUpdateDTO) throws Exception {

        //입력받은 비밀번호
        int insertUpdatePWd = meetUpUpdateDTO.getMeetUpPwd();
        Long updateTargetSeq = meetUpUpdateDTO.getMeetUpSeq();
        MeetUpBoardDetailImg meetUpBoardDetailImg=null;

        MeetUpBoard meetUpBoardPwd = meetUpBoardRepository.findPwdBySeq(updateTargetSeq);
        int boardPwd = meetUpBoardPwd.getMeetUpPwd();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
        Date date = formatter.parse(meetUpUpdateDTO.getMeetUpDeadLine());
        try {
            date = formatter.parse(meetUpUpdateDTO.getMeetUpDeadLine());
        } catch (ParseException e) {
            // meetUpDeadLine 날짜 형식이 잘못된 경우 처리
            throw new GlobalException(ErrorCode.WRONG_DATE);
        }
        System.out.println(meetUpUpdateDTO.getMeetUpName());
        System.out.println(meetUpUpdateDTO.getMeetUpDesc());


        if (insertUpdatePWd == boardPwd) {
            MeetUpBoard meetUpBoard = MeetUpBoard.builder()
                    .meetUpSeq(meetUpUpdateDTO.getMeetUpSeq())
                    .meetUpName(meetUpUpdateDTO.getMeetUpName())
                    .meetUpDesc(meetUpUpdateDTO.getMeetUpDesc())
                    .meetUpDeadLine(date)
                    .meetUpDetailImgSeq(meetUpBoardDetailImg)
                    .build();

            System.out.println("입력되는 이름" + meetUpBoard.getMeetUpName());
            System.out.println("시퀀스시퀀스" + meetUpBoard.getMeetUpSeq());
//            meetUpBoardRepository.updateMeetUp(meetUpBoard);
        }

        return null;
    }

    @Override
    public String deleteBoard(MeetUpDeleteDTO meetUpDeleteDTO) {
        int insertPwd = meetUpDeleteDTO.getCheckPwd();
        //사제하려는 게시글의 Seq
        Long targetSeq = meetUpDeleteDTO.getMeetUpSeq();
        //Seq로  삭제 시도하려는 게시글의 정보 뽑기.
        MeetUpBoard meetUpBoard2 = meetUpBoardRepository.findPwdBySeq(targetSeq);
        //삭제시도하는 게시글의 비밀번호
        int boardPwd = meetUpBoard2.getMeetUpPwd();
        System.out.println("보드비번 : " + boardPwd);
        System.out.println("입력된비번 : " + insertPwd);
        //입력받은 비밀번호랑 삭제 시도하는 게시글 seq로 받아온 비밀번호가 일치하면
        if (boardPwd == insertPwd) {
            //해당 디티오의 seq로 삭제.
            MeetUpBoard meetUpBoard = MeetUpBoard.builder()
                    .meetUpSeq(meetUpDeleteDTO.getMeetUpSeq()).build();
            meetUpBoardRepository.delete(meetUpBoard);
            System.out.println("정상 삭제 되었습니다.");
            return null;
        } else {
            String msg = " 비밀번호가 일치하지 않습니다";
            return msg;
        }
    }

    @Override
    public MeetUpBoard findMeetUpByMeetUpName(String meetUpName) {

        MeetUpBoard meetUpBoard = MeetUpBoard.builder()
                .meetUpName(meetUpName).build();
        meetUpBoardRepository.selectMeetUpByMeetUpName(meetUpName);
        return meetUpBoard;
    }

    @Override
    public List<MeetUpBoard> findMeetUpByInterest(String interest) {

            List<MeetUpBoard> list =meetUpBoardRepository.selectMeetUpBoardByInterest(interest);
        System.out.println("리스트 컨트롤러단"+list);

        return list;
    }

    @Override
    public MeetUpBoard findMeetUpByBoardSeq(Long meetUpSeq) {

        MeetUpBoard meetUpBoardInfo = meetUpBoardRepository.findMeetUpBoardByMeetUpSeq(meetUpSeq);

        return meetUpBoardInfo;
    }

    @Override
    public List<MeetUpBoard> findByMeetUpName(String meetUpName) {

        List<MeetUpBoard> resultList = meetUpBoardRepository.findMeetUPBoardByMeetUpName(meetUpName);
        System.out.println("검색결과: " + resultList);

        return null;
    }


    @Override
    public List<MeetUpBoard> selectAll() {


        List<MeetUpBoard> meetUpBoardList= meetUpBoardRepository.findAll();
        System.out.println("meetUpBoardList"+meetUpBoardList);
        return meetUpBoardList;
    }

    @Override
    public List<Date> findByPartySeq() {
        List<Date> list = meetUpBoardRepository.findAllPartDeadLine();
        System.out.println("list 여기는?:" + list);
        return list;
    }

    //스캐줄러
    //매일 정각에 체크하는기능 .
    @Override
    //    @Scheduled(cron = "0 0 * * * ?") //매시간마다 배포시에는 주석 해제해야함.
    @Scheduled(cron = "0 00 * * * ?") //테스트용
    public void checkDeadLine() {  //받아오는 타입 문제 ..

        List<Date> list = findByPartySeq();
        System.out.println(" 서비스단 리스트 list :" + list);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime truncatedNow = now.withMinute(0).withSecond(0).withNano(0);

//        System.out.println("now:" + now);
        System.out.println("truncatedNow: " + truncatedNow);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        String formattedNow = now.format(formatter);
        System.out.println("formattedNow" + formattedNow);
        for (Date date : list) {
            Date deadList = date;
            String deadListString = date.toString();
            System.out.println("deadList" + deadList);
            System.out.println("date" + date);
            if (deadListString.equals(formattedNow)) {
                //데드라인과 현재시간이 일치하게 되면 해당 데드라인의 시퀀스 정보 가져옴.
                List<Long> meetUpSeq = meetUpBoardRepository.findByPartySeqByDeadLine(deadList);
                System.out.println("해당되는소모임시퀀스:" + meetUpSeq);

                for (Long partSeq : meetUpSeq) {
                    System.out.println("해당되는소모임시퀀스:" + partSeq);
                    //가져온 시퀀스에 해당되는 상태 1로 변경
                    meetUpBoardRepository.updatePartyStatus(partSeq);

                }
            }
        }
    }

}
