package web.mvc.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.user.SmsVerificationDTO;
import web.mvc.service.user.SmsVerificationService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
public class SMSController {

    private final SmsVerificationService smsVerificationService;

    @GetMapping("/{userSeq}")
    public ResponseEntity<?> sendSms(@PathVariable Long userSeq) {
        return ResponseEntity.status(HttpStatus.OK).body(smsVerificationService.sendSms(userSeq));
    }

    @GetMapping("/reissue/{userSeq}")
    public ResponseEntity<?> reSMSVerification(@PathVariable Long userSeq) {
        return ResponseEntity.status(HttpStatus.OK).body(smsVerificationService.reSMSVerification(userSeq));
    }

    @PostMapping("/confirm/{userSeq}")
    public ResponseEntity<?> verifySMS(@RequestBody SmsVerificationDTO sms, @PathVariable Long userSeq){
        return ResponseEntity.status(HttpStatus.OK).body(smsVerificationService.verifySMS(sms.getSmsToken(),userSeq));
    }

}
