package web.mvc.service.admin.report;

import web.mvc.entity.report.Report;

import java.util.List;

public interface AdminReportService {
    /**
     * 신고 목록 전체 출력 (관리자용)
     * */
    List<Report> findAllReportList();

    /**
     * 신고글 보기
     * */
    Report selectReportBySeq(Long reportSeq);

    /**
     * url을 타고 신고한 게시글로 이동하기 위해 신고한 게시글의 url 반환
     */
    String getReportedUrl(Report report);

    /**
     * 신고 처리상태 업데이트 (처리중 || 처리완료)
     * */
    Report updateReportStatus(Report report);

    /**
     * 신고 처리결과 업데이트 (무혐의 || 3일정지 || 영구정지)
     * */
    Report updateReportResult(Report report);
}
