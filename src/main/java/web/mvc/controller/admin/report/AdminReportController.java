package web.mvc.controller.admin.report;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.mvc.entity.report.Report;
import web.mvc.service.admin.report.AdminReportService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/report")
@Slf4j
public class AdminReportController {

    private final AdminReportService adminReportService;

    /**
     * 신고 목록 전체 출력 (관리자용)
     * */
    @GetMapping("/")
    public ResponseEntity<?> getReportList() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(adminReportService.findAllReportList());
    }

    /**
     * 신고글 보기
     * */
    @GetMapping("/{reportSeq}")
    public ResponseEntity<?> getReportBySeq(@RequestBody Report report) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(adminReportService.selectReportBySeq(report.getReportSeq()));
    }

    /**
     * url을 타고 신고한 게시글로 이동
     */
    @GetMapping("/moveToUrl")
    public void moveToUrl(@RequestBody Report report, HttpServletResponse response) throws IOException {
        String url = adminReportService.getReportedUrl(report);
        response.sendRedirect(url);
    }

//    @GetMapping("/{reportUrl}")
//    public ResponseEntity<?> moveToUrl(@RequestBody Report report) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(adminReportService.getReportedUrl(report));
//    }

    /**
     * 신고 처리상태 업데이트 (처리중 || 처리완료)
     * */
    @GetMapping("/{reportStatus}")
    public ResponseEntity<?> updateReportStatus(@RequestBody Report report) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(adminReportService.updateReportStatus(report));
    }

    /**
     * 신고 처리결과 업데이트 (무혐의 || 3일정지 || 영구정지)
     * */
    @GetMapping("/{reportResult}")
    public ResponseEntity<?> updateReportResult(@RequestBody Report report) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(adminReportService.updateReportResult(report));
    }

}
