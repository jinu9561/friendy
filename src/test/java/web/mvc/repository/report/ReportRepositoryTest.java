package web.mvc.repository.report;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
@Transactional
@Commit
public class ReportRepositoryTest {

    @Autowired
    ReportRepository reportRepository;
    @Autowired
    ReportTypeRepository reportTypeRepository;




}
