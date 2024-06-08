package web.mvc.config.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.mvc.domain.user.Users;
import web.mvc.repository.user.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        log.info("username = {}",userId);
        //DB에서 select..
        Users findUsers = userRepository.findUserByUserId(userId); // pk가 아닌 userId 속성으로
        if(findUsers != null){
            log.info("findMember = {}",findUsers);
            return new CustomMemberDetails(findUsers);
        }

        return null;
    }
}
