package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.domain.user.EmailVerification;

public interface EmaillVerificationRepository extends JpaRepository<EmailVerification,Long> {

    @Query("select e from EmailVerification e left join fetch e.user where e.emailToken = ?1 and e.user.userSeq = ?2")
    public EmailVerification findByEmailToken(String emailToken,Long userSeq);

    @Query("select e from EmailVerification e left join fetch e.user where e.user.userSeq = ?1")
    public EmailVerification findByUserSeq(Long userSeq);

}
