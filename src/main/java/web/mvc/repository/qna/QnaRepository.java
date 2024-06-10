package web.mvc.repository.qna;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.mvc.entity.qna.Qna;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {

    private Logger log = LoggerFactory.getLogger(QnaRepository.class);

    @Query("select q from Qna q left join fetch q.user where q.user.userSeq = ?1")
    public List<Qna> findByQnaByUser(Long userSeq);

//    @Query("select q from Qna q where q.qnaDesc = ?1")
//    public Qna findReplyByQuestion(String question);

    @Query("select q from Qna q where q.qnaDesc like %?1%")
    public List<Qna> findRepliesByQuestionContaining(String qnaDesc);

}
