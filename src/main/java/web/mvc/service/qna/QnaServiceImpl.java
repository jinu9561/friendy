package web.mvc.service.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.mvc.entity.qna.Qna;
import web.mvc.repository.qna.QnaRepository;

import java.util.Optional;


@Service
public class QnaServiceImpl implements QnaService{

    @Autowired
    private QnaRepository qnaRepository;

    public String getQnaReply(String qnaDesc) {
        Optional<Qna> qna = qnaRepository.findByQuestion(qnaDesc);
        return qna.map(Qna::getQnaReply)
                .orElse("Sorry, I don't know the answer to that question.");

    }
}
