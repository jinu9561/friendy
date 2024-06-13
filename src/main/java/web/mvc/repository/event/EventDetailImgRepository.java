package web.mvc.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.entity.event.EventDetailImg;

import java.util.List;

public interface EventDetailImgRepository extends JpaRepository<EventDetailImg, Long> {
    @Query("select e from EventDetailImg e where e.event.eventSeq = ?1")
    public List<EventDetailImg> findByEventSeq(Long eventSeq);
}
