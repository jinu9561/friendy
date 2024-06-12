package web.mvc.service.report;

import org.springframework.stereotype.Service;
import web.mvc.entity.report.Report;
import web.mvc.entity.report.ReportType;

@Service
public interface ReportService {

    /**
     * 신고하기 (유저용)
     */
    void insertReport(Report report, ReportType reportType);



}
