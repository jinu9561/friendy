package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.user.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
