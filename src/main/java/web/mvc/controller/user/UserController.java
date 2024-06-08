package web.mvc.controller.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.user.UsersDTO;
import web.mvc.service.user.UserService;



@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;


    @PostMapping("/join")
    public ResponseEntity<?> register(@RequestBody UsersDTO usersDTO) {
        log.info("Registering user: {}", usersDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userService.registerUser(usersDTO));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> duplicateIdCheck(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.duplicateIdCheck(userId));
    }





}
