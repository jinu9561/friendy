package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.user.Interest;

public interface InterestRepository extends JpaRepository<Interest, Long> {

    @Modifying
    @Query("delete from Interest i where i.profile.profileSeq = ?1")
    public int deleteByProfileSeq(Long profileSeq);
}
