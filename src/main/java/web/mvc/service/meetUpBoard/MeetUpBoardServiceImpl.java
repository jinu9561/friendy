package web.mvc.service.meetUpBoard;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.dto.meetUpBoard.MeetUpBoardDTO;
import web.mvc.entity.meetUpBoard.MeetUpBoard;
import web.mvc.repository.partyRoom.MeetUpBoardDetailImgRepository;
import web.mvc.repository.partyRoom.MeetUpBoardRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class MeetUpBoardServiceImpl implements MeetUpBoardService {

    private final MeetUpBoardRepository meetUpBoardRepository;
    private final MeetUpBoardDetailImgRepository partyBoardDetailImgRepository;


    @Override
    public String createParty(MeetUpBoardDTO meetUpBoardDTO) {
//        PartyBoard partyBoard = PartyBoard.builder()
//                .partyName(partyBoardDTO.getPartyName())
//                .userSeq(partyBoardDTO.getUserSeq())
//                .partyDesc(partyBoardDTO.getPartyDesc())
//                .partyMainImg(partyBoardDTO.getPartyMainImg())
//                .partyBoardPwd(partyBoardDTO.getPartyBoardPwd())
//                .partyMaxEntry(partyBoardDTO.getPartyMaxEntry())
//                .partDeadLine(partyBoardDTO.getPartDeadLine())
//                .partyStatus(PartyBoardStatus.NORMAL.getFlag())
//                .build();
        MeetUpBoard meetUpBoard = MeetUpBoard.builder()
                .meetUpName(meetUpBoardDTO.getMeetUpName())
                .userSeq(meetUpBoardDTO.getUserSeq())
                .meetUpDesc(meetUpBoardDTO.getMeetUpDesc())
                .meetUpMainImg(meetUpBoardDTO.getMeetUpMainImg())
                .meetUpPwd(meetUpBoardDTO.getMeetUpPwd())
                .meetUpDeadLine(meetUpBoardDTO.getMeetUpDeadLine())
                .meetUpMaxEntry(meetUpBoardDTO.getMeetUpMaxEntry())
                .meetUpStatus(meetUpBoardDTO.getMeetUpStatus())
                .build();
        System.out.println("그럼 여기까지 ?여기까지왔냐 ");
        try {
            meetUpBoardRepository.save(meetUpBoard);
            return "성공";
        } catch (Exception e) {
            return "생성실패";
        }

    }

    @Override
    public String updateBoard(MeetUpBoardDTO partyBoardDTO) {
        return null;
    }

    @Override
    public String deleteBoard(Long partySeq) {
        return null;
    }

    @Override
    public List<MeetUpBoard> selectAll() {
        return null;
    }

    @Override
    public List<Date> findByPartySeq() {
        List<Date> list = meetUpBoardRepository.findAllPartDeadLine();
        System.out.println("list:" + list);
        return null;
    }

    //스캐줄러
    //매일 정각에 체크하는기능 .
    @Override
    //    @Scheduled(cron = "0 0 * * * ?") //매시간마다 배포시에는 주석 해제해야함.

    @Scheduled(cron = "0 35 01 * * ?") //테스트용
    public void checkDeadLine() {  //받아오는 타입 문제 ..

        List<Date> list = findByPartySeq();
        System.out.println("list:" + list);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH");
        String formattedNow = now.format(formatter);
        System.out.println(formattedNow);
        for (Date date : list) { // 향상된 for문 사용
            // 여기에서 각 date에 대한 처리를 수행합니다.
            System.out.println(date);

            List<Long> Seq = meetUpBoardRepository.findByPartySeqByDeadLine(date);
            for (Long partSeq : Seq) {
                System.out.println("파티시퀀스 :" + partSeq);
//                partyBoardRepository.updatePartyStatus(partSeq);
            }
        }
    }

}
