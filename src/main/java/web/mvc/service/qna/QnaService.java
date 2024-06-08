package web.mvc.service.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.mvc.entity.qna.Qna;
import web.mvc.repository.qna.QnaRepository;

import java.util.Optional;

@Service
public interface QnaService {

    public String getQnaReply(String qnaDesc);

}
