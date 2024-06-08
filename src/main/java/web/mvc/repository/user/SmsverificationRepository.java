package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.domain.user.SmsVerification;

public interface SmsverificationRepository extends JpaRepository<SmsVerification,Long> {

    @Query("select s from SmsVerification s left join fetch s.user where s.user.userSeq = ?1")
    public SmsVerification findByUserSeq(Long userSeq);

    @Query("select s from SmsVerification s left join fetch s.user where s.smsToken = ?1 and s.user.userSeq = ?2")
    public SmsVerification findBySmsToken(String smsToken, Long userSeq);
}
