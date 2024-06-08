package web.mvc.repository.qna;

import org.springframework.stereotype.Repository;
import web.mvc.entity.qna.Qna;

import java.util.Optional;

@Repository
public interface QnaRepository {

    Optional<Qna> findByQuestion(String question);

}
