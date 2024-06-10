package web.mvc.service.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.mvc.dto.user.*;
import web.mvc.entity.user.Profile;
import web.mvc.entity.user.ProfileDetailImg;
import web.mvc.entity.user.UserDetail;
import web.mvc.entity.user.Users;
import web.mvc.exception.common.GlobalException;
import web.mvc.exception.user.ErrorCode;
import web.mvc.repository.user.ProfileDetailImgRepository;
import web.mvc.repository.user.ProfileRepository;
import web.mvc.repository.user.UserDetailRepository;
import web.mvc.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final ProfileRepository profileRepository;
    private final ProfileDetailImgRepository profileDetailImgRepository;

    private String resultMsg ="변경이 완료되었습니다.!";

    @Override
    public List<UsersDTO> getUsersList() {
      List<UsersDTO> list = new ArrayList<>();

      List<Users> usersList = userRepository.findAll().stream().toList();

      for (Users users : usersList) {

          String role = users.getRole();

          if(!role.equals("ROLE_ADMIN") ) {
              UserDetail userDetail = userDetailRepository.findByUserSeq(users.getUserSeq());

              UserDetailDTO userDetailDTO  = UserDetailDTO.builder()
                      .UserDetailSeq(userDetail.getUserDetailSeq())
                      .userState(userDetail.getUserState())
                      .userRegDate(userDetail.getUserRegDate())
                      .userUpdateDate(userDetail.getUserUpdateDate())
                      .userRate(userDetail.getUserRate())
                      .userJelly(userDetail.getUserJelly())
                      .lastLoginDate(userDetail.getLastLoginDate())
                      .build();

              UsersDTO usersDTO = UsersDTO.builder()
                      .userSeq(users.getUserSeq())
                      .userId(users.getUserId())
                      .userPwd(users.getUserPwd())
                      .userName(users.getUserName())
                      .nickName(users.getNickName())
                      .birth(users.getBirth().toString())
                      .address(users.getAddress())
                      .email(users.getEmail())
                      .phone(users.getPhone())
                      .country(users.getCountry())
                      .userDetail(userDetailDTO)
                      .gender(users.getGender())
                      .build();

              list.add(usersDTO);
          }

      }
      return list;
    }

    @Override
    public String alterUserDetail(Long userSeq,UserDetailDTO userDetailDTO) {
        Users user = userRepository.findById(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));

        user.getUserDetail().setUserState(userDetailDTO.getUserState());
        user.getUserDetail().setUserRegDate(userDetailDTO.getUserRegDate());
        user.getUserDetail().setUserJelly(userDetailDTO.getUserJelly());

        return resultMsg;
    }

    @Override
    public List<UserProfileDTO> getProfileList() {
        return profileRepository.findAllProfiles();
    }

    @Override
    public List<UserProfileDetailDTO> getProfileDetail() {
        return profileRepository.findUserProfileDetails();
    }

    @Override
    public String alterProfileState(UserProfileDTO userProfileDTO) {
        Profile profile = profileRepository.findByUserSeq(userProfileDTO.getUserSeq()).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));
        profile.setImgStatus(userProfileDTO.getImgStatus());

        return resultMsg;
    }

    @Override
    public String alterProfileDetail(UserProfileDetailDTO userProfileDetailDTO) {
        ProfileDetailImg profileDetailImg = profileDetailImgRepository.findByProfileSeq(userProfileDetailDTO.getProfileSeq()).orElseThrow(()->
                new GlobalException(ErrorCode.NOTFOUND_PROFILE));
        profileDetailImg.setImgStatus(userProfileDetailDTO.getImgStatus());

        return resultMsg;
    }
}
