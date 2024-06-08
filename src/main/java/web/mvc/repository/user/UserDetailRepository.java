package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.user.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
}
