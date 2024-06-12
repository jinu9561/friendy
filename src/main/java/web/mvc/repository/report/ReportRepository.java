package web.mvc.repository.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.mvc.entity.report.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    /**
     * 신고상태 변경
     * */
    @Modifying
    @Query("update Report r set r.reportStatus = :reportStatus where r.reportSeq = :reportSeq")
    Report updateReportStatus(@Param("reportStatus") int reportStatus, @Param("reportSeq") Long reportSeq);

    /**
     * 신고결과 변경
     * */
    @Modifying
    @Query("update Report r set r.reportResult = :reportResult where r.reportSeq = :reportSeq")
    Report updateReportResult(@Param("reportStatus") int reportResult, @Param("reportSeq") Long reportSeq); //entity로 바인딩 해보는것도 알아보장


}
