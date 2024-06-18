package web.mvc.repository.user;

import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.user.Interest;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Long> {

//    @Modifying
//    @Query("delete from Interest i where i.profile.profileSeq = ?1")
//    public int deleteByProfileSeq(Long profileSeq);
//
//    @Query("select i from Interest i where i.profile.profileSeq = ?1")
//    public List<Interest> findByProfileSeq(Long profileSeq);

    @Query("select i from Interest  i where  i.interestCategory = ?1")
    public Interest findByInterestCategory(String interestCategory);

}
