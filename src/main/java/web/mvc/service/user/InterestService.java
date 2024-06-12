package web.mvc.service.user;

import web.mvc.dto.user.InterestDTO;

import java.util.List;

public interface InterestService {

    // 관심사 리스트 가져오기
    public List<InterestDTO> getInterest();
}
