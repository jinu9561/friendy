package web.mvc.controller.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.user.EmailVerificationDTO;
import web.mvc.service.user.EmailVerificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
@Slf4j
public class EmailController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/{userSeq}")
    public ResponseEntity<?> verifyEmail(@RequestBody EmailVerificationDTO emailVerificationDTO, @PathVariable Long userSeq) {
        log.info("token: " + emailVerificationDTO.getEmailToken());
        return ResponseEntity.status(HttpStatus.CREATED).body(emailVerificationService.verifyEmail(emailVerificationDTO.getEmailToken(),userSeq));
    }

    @GetMapping("/reissue/{userSeq}")
    public ResponseEntity<?> reEmailVerification(@PathVariable Long userSeq) {
        return ResponseEntity.status(HttpStatus.OK).body(emailVerificationService.reEmailVerification(userSeq));
    }
}
