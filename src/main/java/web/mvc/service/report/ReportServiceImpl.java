package web.mvc.service.report;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.mvc.entity.report.Report;
import web.mvc.entity.report.ReportType;
import web.mvc.repository.report.ReportRepository;
import web.mvc.repository.report.ReportTypeRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReportServiceImpl implements ReportService{


    private final ReportRepository reportRepository;
    private final ReportTypeRepository reportTypeRepository;

    @Override
    public void insertReport(Report report, ReportType reportType) {
        ReportType savedReportType = reportTypeRepository.save(reportType);
        Report savedReport = reportRepository.save(report);

        log.info("savedReportType", savedReportType);
        log.info("savedReport", savedReport);
    }

}
