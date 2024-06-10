package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.dto.user.UserProfileDTO;
import web.mvc.dto.user.UserProfileDetailDTO;
import web.mvc.entity.user.Profile;
import web.mvc.entity.user.ProfileDetailImg;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("select p from Profile p where p.user.userSeq = ?1")
    public Optional<Profile> findByUserSeq(Long userSeq);

    // users와 profile을 join하여 유저의 이름, 닉네임, profile의 대표사진 조회
    @Query("select new web.mvc.dto.user.UserProfileDTO (u.userSeq, u.userName, u.nickName,u.email,u.phone, p.profileMainImg,p.imgStatus,p.introduce) " +
            "from Users u join u.profile p ")
    List<UserProfileDTO> findAllProfiles();

    // users, profile을 join하여 유저 프로필의 세부 사진도 같이 조회
    @Query("select new web.mvc.dto.user.UserProfileDetailDTO (u.userSeq, u.userName, u.nickName,u.email,u.phone, pd.profileDetailImgSrc, pd.imgStatus, p.profileSeq) " +
            "from Users u join u.profile p join p.profileDetailImgList pd")
    List<UserProfileDetailDTO> findUserProfileDetails();

}
