package web.mvc.service.user;


import web.mvc.dto.user.EmailVerificationDTO;
import web.mvc.dto.user.UsersDTO;



public interface UserService {

    // 가입 하기
    public String registerUser(UsersDTO usersDTO) ;

    // id 중복 체크
    public boolean duplicateIdCheck(String userId);

    // 개인 정보 수정하기
    public String alter(Long userSeq,UsersDTO usersDTO);

    // 비밀 번호 찾기 -> 인증 번호 발급 - > 비밀번호 변경
    public String findPasswordProve(UsersDTO usersDTO);

    // 비밀 번호 변경
    public String alterPassword(EmailVerificationDTO emailVerificationDTO);

    // 회원 탈퇴
    public String resign(Long userSeq);


}
