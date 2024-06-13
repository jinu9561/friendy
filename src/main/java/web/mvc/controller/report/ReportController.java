package web.mvc.controller.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.entity.report.Report;
import web.mvc.entity.report.ReportType;
import web.mvc.service.report.ReportService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class ReportController {

    private final ReportService reportService;

    /**
     * 신고하기
     * */
    @GetMapping("/send")
    public ResponseEntity<?> insertReport(@RequestBody Report report
            , @RequestBody ReportType reportType) {

        reportService.insertReport(report, reportType);
        return ResponseEntity.status(HttpStatus.OK).body("성공");
    }


}
