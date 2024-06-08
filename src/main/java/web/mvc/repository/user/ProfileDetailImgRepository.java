package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.user.ProfileDetailImg;

public interface ProfileDetailImgRepository extends JpaRepository<ProfileDetailImg, Long> {
}
