package web.mvc.entity.report;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY ,generator = "report_type_seq")
    @SequenceGenerator(name ="report_type_seq" , allocationSize = 1 , sequenceName = "report_type_seq")
    private Long reportTypeSeq;

    private int reportType;  // 신고할 게시물의 유형 (0=댓글, 1=채팅방, 2=프로필,3=소모임게시글,4=커뮤니티게시글,5=사진게시글)

    private Long seqByType;
}
