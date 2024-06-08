package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.user.JellyRecord;

public interface JellyRepository extends JpaRepository<JellyRecord,Long> {
}
