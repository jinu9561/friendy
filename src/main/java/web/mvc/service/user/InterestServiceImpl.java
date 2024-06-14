package web.mvc.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import web.mvc.dto.user.InterestDTO;
import web.mvc.dto.user.UsersDTO;
import web.mvc.entity.user.Interest;
import web.mvc.entity.user.Profile;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.user.InterestRepository;
import web.mvc.repository.user.ProfileRepository;
import web.mvc.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InterestServiceImpl implements InterestService {

    private final InterestRepository interestRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    private List<InterestDTO> initiInterestList;
    private String alterMsg ="등록 완료 됬습니다";

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        initiInterestList = new ArrayList<>();
        initiInterestList.add(InterestDTO.builder().interestSeq(1L).interestCategory("피트니스").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(2L).interestCategory("수영").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(3L).interestCategory("러닝").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(4L).interestCategory("산책").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(5L).interestCategory("독서").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(6L).interestCategory("영화").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(7L).interestCategory("요리").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(8L).interestCategory("여행").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(9L).interestCategory("음악").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(10L).interestCategory("미술").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(11L).interestCategory("사진").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(12L).interestCategory("쇼핑").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(13L).interestCategory("명상").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(14L).interestCategory("축구").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(15L).interestCategory("농구").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(16L).interestCategory("테니스").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(17L).interestCategory("배드민턴").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(18L).interestCategory("볼링").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(19L).interestCategory("반려 동물").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(20L).interestCategory("노래").build());
        initiInterestList.add(InterestDTO.builder().interestSeq(21L).interestCategory("캠핑").build());



    }

    @Override
    public List<InterestDTO> getInterest() {
        log.info(initiInterestList.toString());
        return initiInterestList;
    }

    @Override
    public String alterInterest(Long userSeq, UsersDTO usersDTO) {

        Profile profile = profileRepository.findByUserSeq(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PROFILE));

        List<Interest> interestList = interestRepository.findByProfileSeq(profile.getProfileSeq());
        List<String> interestCategoryList = usersDTO.getInterestCategory();

        for(int i = 0; i< interestList.size(); i++) {
            String interestCategory = interestCategoryList.get(i);
            Interest interest = interestList.get(i);
            interest.setInterestCategory(interestCategory);
        }

        interestRepository.saveAll(interestList);

        return alterMsg;
    }
}
