package web.mvc.entity.qna;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaSeq;
    private String qnaDesc;
    private LocalDateTime qnaRegDate;
    private String qnaReply;

}
