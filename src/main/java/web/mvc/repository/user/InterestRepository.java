package web.mvc.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.user.Interest;

public interface InterestRepository extends JpaRepository<Interest, Long> {
}
