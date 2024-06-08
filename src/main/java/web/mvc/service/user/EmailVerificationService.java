package web.mvc.service.user;

import jakarta.mail.MessagingException;
import web.mvc.domain.user.EmailVerification;
import web.mvc.domain.user.Users;

public interface EmailVerificationService {

    // 이메일 확인
    public boolean verifyEmail(String emailToken,Long userSeq);

    // 이메일 토큰 저장
    public EmailVerification saveEmailToken(Users user);

    // 이메일 보내기
    public void sendEmailVerificationCode(String to, String subject, String text) throws MessagingException;

    // 이메일 토큰 찾기
    public String getEmailToken(Long userSeq);

    // 이메일 재발급
    public String reEmailVerification(Long userSeq);
}
