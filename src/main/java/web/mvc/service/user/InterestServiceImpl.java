package web.mvc.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import web.mvc.dto.user.InterestDTO;
import web.mvc.entity.user.Interest;
import web.mvc.repository.user.InterestRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InterestServiceImpl implements InterestService {

    private final InterestRepository interestRepository;

    private List<InterestDTO> initiInterestList;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        initiInterestList = new ArrayList<>();
        initiInterestList.add(InterestDTO.builder().interestCategory("피트니스").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("수영").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("러닝").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("산책").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("독서").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("영화").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("요리").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("여행").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("음악").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("미술").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("사진").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("쇼핑").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("명상").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("축구").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("농구").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("테니스").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("배드민턴").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("볼링").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("반려 동물").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("노래").build());
        initiInterestList.add(InterestDTO.builder().interestCategory("캠핑").build());



    }

    @Override
    public List<InterestDTO> getInterest() {
        log.info(initiInterestList.toString());
        return initiInterestList;
    }
}
