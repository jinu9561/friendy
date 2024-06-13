package web.mvc.service.report;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import web.mvc.repository.report.ReportRepository;
import web.mvc.repository.report.ReportTypeRepository;
import web.mvc.service.admin.report.AdminReportService;
import web.mvc.service.user.UserService;

@SpringBootTest
@Transactional
@Commit
public class ReportServiceTest {

    @Autowired
    ReportRepository reportRepository;
    @Autowired
    ReportTypeRepository reportTypeRepository;

    @Autowired
    ReportService reportService;
    @Autowired
    AdminReportService adminReportService;
    @Autowired
    UserService userService;

    @Test
    void insert() {
    }


}
