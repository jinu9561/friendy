package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.user.EmailVerification;

import java.util.Optional;

public interface EmaillVerificationRepository extends JpaRepository<EmailVerification,Long> {

    @Query("select e from EmailVerification e left join fetch e.user where e.emailToken = ?1 and e.user.userSeq = ?2")
    public Optional<EmailVerification> findByEmailToken(String emailToken,Long userSeq);

    @Query("select e from EmailVerification e left join fetch e.user where e.user.userSeq = ?1")
    public Optional<EmailVerification> findByUserSeq(Long userSeq);

}
