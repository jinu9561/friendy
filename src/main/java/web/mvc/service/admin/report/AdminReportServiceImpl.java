package web.mvc.service.admin.report;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import web.mvc.dto.report.ReportDTO;
import web.mvc.entity.report.Report;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.report.ReportRepository;
import web.mvc.repository.report.ReportTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AdminReportServiceImpl implements AdminReportService {

    private final ReportRepository reportRepository;
    private final ReportTypeRepository reportTypeRepository;

    @Override
    public List<ReportDTO> findAllReportList() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ReportDTO selectReportBySeq(Long reportSeq) {
        Report report = reportRepository.findById(reportSeq).orElseThrow(() -> new RuntimeException("Report not found"));
        return convertToDTO(report);
    }

    private ReportDTO convertToDTO(Report report) {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReportSeq(report.getReportSeq());
        reportDTO.setSenderSeq(report.getSender().getUserSeq());
        reportDTO.setReceiverSeq(report.getReceiver() != null ? report.getReceiver().getUserSeq() : null);
        reportDTO.setReportDescription(report.getReportDescription());
        reportDTO.setReportRegDate(report.getReportRegDate());
        reportDTO.setReportResult(report.getReportResult());
        reportDTO.setReportStatus(report.getReportStatus());
        reportDTO.setReportTypeSeq(report.getReportType().getReportTypeSeq());
        reportDTO.setReportUrl(report.getReportUrl());
        return reportDTO;
    }

    @Override
    public String getReportedUrl(ReportDTO reportDTO) {
        String url = reportDTO.getReportUrl();

        log.info("url : {}", url);

        return "url";
    }

    @Override
    public ReportDTO updateReportStatus(ReportDTO reportDTO) {
        int rowsUpdated = reportRepository.updateReportStatus(reportDTO.getReportStatus(), reportDTO.getReportSeq());
        if (rowsUpdated == 0) {
            throw new GlobalException(ErrorCode.NOTFOUND_ID);
        }
        Report updatedReport = reportRepository.findById(reportDTO.getReportSeq())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOTFOUND_ID));
        ReportDTO updatedReportDTO = convertToDTO(updatedReport);
        log.info("Updated status report: {}", updatedReportDTO);
        return updatedReportDTO;
    }

    @Override
    public ReportDTO updateReportResult(ReportDTO reportDTO) {
        int rowsUpdated = reportRepository.updateReportResult(reportDTO.getReportResult(), reportDTO.getReportSeq());
        if (rowsUpdated == 0) {
            throw new GlobalException(ErrorCode.NOTFOUND_ID);
        }
        Report updatedReport = reportRepository.findById(reportDTO.getReportSeq())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOTFOUND_ID));
        ReportDTO updatedReportDTO = convertToDTO(updatedReport);
        log.info("Updated result report: {}", updatedReportDTO);
        return updatedReportDTO;
    }

}
