package web.mvc.service.user;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.mvc.domain.user.*;
import web.mvc.dto.user.AdminDTO;
import web.mvc.dto.user.InterestDTO;
import web.mvc.dto.user.UsersDTO;
import web.mvc.enums.users.State;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.user.UserDetailRepository;
import web.mvc.repository.user.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final EmailVerificationService emailVerificationService;

    private final AdminDTO adminDTO;
    private String subject ="Friendy 가입 인증코드 입니다!";
    private String content ="다음 인증 코드를 사용하여 회원가입을 완료하세요: {code}";
    private String failMsg = "메일 발송을 실패했습니다";
    private String registerMsg = "해당 이메일로 인증 코드를 보냈습니다 확인해 주세요";


    @EventListener(ApplicationReadyEvent.class)
    public void handleApplicationReadyEvent(ApplicationReadyEvent event) {
        log.info("Application ready");
        log.info("adminDTO: " + adminDTO);
        if(userRepository.findUserByUserId(adminDTO.getUserId())==null) {
            //생년월일 date형식으로 바꿔기
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            // 잘못된 날짜를 자동으로 유효한 날짜로 변환하는 기능 비활성화 -> 잘못된 날짜는 바로 예외가 나와야 되기 때문에
            formatter.setLenient(false);
            Date birth = null;

            try {
                birth = formatter.parse(adminDTO.getBirth());
            } catch (ParseException e) {
                //throw new GlobalException(ErrorCode.WRONG_DATE);
            }

            String encodePwd = passwordEncoder.encode(adminDTO.getUserPwd());

            Users admin = Users.builder()
                    .userId(adminDTO.getUserId())
                    .userName(adminDTO.getUserName())
                    .userPwd(encodePwd)
                    .nickName(adminDTO.getNickName())
                    .birth(birth)
                    .address(adminDTO.getAddress())
                    .email(adminDTO.getEmail())
                    .phone(adminDTO.getPhone())
                    .country(adminDTO.getCountry())
                    .gender(adminDTO.getGender())
                    .Role("ROLE_ADMIN")
                    .build();

            Users savedAdmin = userRepository.save(admin);
        }
    }


    @Override
    public String registerUser(UsersDTO usersDTO) {

        //생년월일 date형식으로 바꿔기
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        // 잘못된 날짜를 자동으로 유효한 날짜로 변환하는 기능 비활성화 -> 잘못된 날짜는 바로 예외가 나와야 되기 때문에
        formatter.setLenient(false);
        Date birth = null;

        try {
            birth = formatter.parse(usersDTO.getBirth());

            if(userRepository.findUserByUserId(usersDTO.getUserId()) != null){
                //해당 아이디 존재
                throw new GlobalException(ErrorCode.FIND_ID);
            }

            // 비번 암호화
            String encodePwd  = passwordEncoder.encode(usersDTO.getUserPwd());

            Users user = Users.builder()
                    .userId(usersDTO.getUserId())
                    .userName(usersDTO.getUserName())
                    .userPwd(encodePwd)
                    .nickName(usersDTO.getNickName())
                    .birth(birth)
                    .address(usersDTO.getAddress())
                    .email(usersDTO.getEmail())
                    .phone(usersDTO.getPhone())
                    .country(usersDTO.getCountry())
                    .gender(usersDTO.getGender())
                    .build();

            // 저장하기 전에 UserDetail, profile 생성
            UserDetail userDetail = new UserDetail(user);
            Profile profile = new Profile(user);
            userDetail.setUserState(State.WAITING);

            // user에 userDetail, profile 저장
            user.setUserDetail(userDetail);
            user.setProfile(profile);
            // user에 관심사 저장
            List<Interest> savedList  = user.getProfile().getInterestList();
            List<InterestDTO> interests = usersDTO.getInterests();

            for(InterestDTO i : interests){
                Interest interest = Interest.builder()
                        .interestCategory(i.getInterestCategory())
                        .profile(profile) // Associate the interest with the profile
                        .build();
                savedList.add(interest);
            }

            // db에 유저 저장
            Users savedUsers = userRepository.save(user);


            // 이메일 인증을 위한 emailVerification 저장
            EmailVerification savedEmailVerification = emailVerificationService.saveEmailToken(savedUsers);
            // 이메일 인증토큰 보내기
            content = content.replace("{code}", savedEmailVerification.getEmailToken());
            emailVerificationService.sendEmailVerificationCode(user.getEmail(),subject,content);

            log.info("savedUsers = {}", savedUsers);


        } catch (ParseException e) {
            throw new GlobalException(ErrorCode.WRONG_DATE);
        } catch (MessagingException m){
            return failMsg;
        }

        return registerMsg;
    }

    @Override
    public boolean duplicateIdCheck(String userId) {
        if(userRepository.findUserByUserId(userId)!= null){
            // 해당 id가 있다
            return  true;
        }
        
        return false;
    }

}
