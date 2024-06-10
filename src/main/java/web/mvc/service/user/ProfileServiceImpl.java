package web.mvc.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.user.ProfileDTO;
import web.mvc.entity.user.Interest;
import web.mvc.entity.user.Profile;
import web.mvc.entity.user.ProfileDetailImg;
import web.mvc.entity.user.Users;
import web.mvc.dto.user.InterestDTO;
import web.mvc.dto.user.ProFileDetailImgDTO;
import web.mvc.enums.users.ImgStatus;
import web.mvc.exception.user.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.user.InterestRepository;
import web.mvc.repository.user.ProfileDetailImgRepository;
import web.mvc.repository.user.ProfileRepository;
import web.mvc.repository.user.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileDetailImgRepository profileDetailImgRepository;
    private final InterestRepository interestRepository;

    @Value("${profile.save.dir}")
    private String uploadDir;
    private String uploadImgMsg = "사진 등록에 성공했습니다. 관리자의 승인을 기다려주세요!";
    private String uploadMsg ="업로드에 성공했습니다";

    @Override
    public Map<String, Object> getProfile(Long userSeq) {
        Map<String, Object> map = new HashMap<String,Object>();

        Users user = userRepository.findById(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));
        Profile profile = profileRepository.findByUserSeq(user.getUserSeq()).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PROFILE));

        map.put("userName", user.getUserName());
        map.put("nickName", user.getNickName());
        map.put("country",user.getCountry());
        map.put("gender",user.getGender());
        map.put("userJelly",user.getUserDetail().getUserJelly());
        map.put("introduce",profile.getIntroduce());
        map.put("profileMainImg",profile.getProfileMainImg());

        List<ProFileDetailImgDTO> profileDetailImgDTOList = profile.getProfileDetailImgList().stream()
                .map(img -> {
                    ProFileDetailImgDTO dto = new ProFileDetailImgDTO();
                    dto.setProfileDetailImgSeq(img.getProfileDetailImgSeq());
                    dto.setProfileDetailImgSrc(img.getProfileDetailImgSrc());
                    dto.setProfileDetailImgType(img.getProfileDetailImgType());
                    dto.setProfileDetailImgSize(img.getProfileDetailImgSize());
                    return dto;
                }).collect(Collectors.toList());
        map.put("profileDetailImgList",profileDetailImgDTOList);

        List<InterestDTO> interestDTOList = profile.getInterestList().stream()
                .map(interest -> {
                    InterestDTO dto = new InterestDTO();
                    dto.setInterestSeq(interest.getInterestSeq());
                    dto.setInterestCategory(interest.getInterestCategory());
                    return dto;
                }).collect(Collectors.toList());
        map.put("interestList",interestDTOList);


        return map;
    }

    @Override
    public String uploadMainPicture(Long userSeq, MultipartFile file) {
        Profile profile = profileRepository.findByUserSeq(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PROFILE));
        Map<String, String>map =this.uploadFile(true,file);

        profile.setProfileMainImg(map.get("imgSrc"));
        profile.setProfileMainImgSize(map.get("imgSize"));
        profile.setProfileMainImgType(map.get("imgType"));
        profile.setImgStatus(ImgStatus.PENDING);

        return uploadImgMsg;
    }

    @Override
    public String uploadDetail(Long userSeq, MultipartFile file) {
        Profile profile = profileRepository.findByUserSeq(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PROFILE));
        Map<String, String>map =this.uploadFile(false,file);
        ProfileDetailImg profileDetailImg = new ProfileDetailImg(profile);

        profileDetailImg.setProfileDetailImgSrc(map.get("imgSrc"));
        profileDetailImg.setProfileDetailImgType(map.get("imgType"));
        profileDetailImg.setProfileDetailImgSize(map.get("imgSize"));
        profileDetailImg.setImgStatus(ImgStatus.PENDING);

        profileDetailImgRepository.save(profileDetailImg);

        return uploadImgMsg;
    }

    @Override
    public String alterProfile(Long userSeq, ProfileDTO profileDTO) {
        Profile profile = profileRepository.findByUserSeq(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PROFILE));

        // 기존 관심목록 삭제
        interestRepository.deleteByProfileSeq(profile.getProfileSeq());

        // 새로운 관심사 목록을 추가
        List<InterestDTO> interests = profileDTO.getInterestList();
        List<Interest> newInterestList = interests.stream()
                .map(i -> Interest.builder()
                        .interestCategory(i.getInterestCategory())
                        .profile(profile)
                        .build())
                .collect(Collectors.toList());


        profile.setInterestList(newInterestList);
        profile.setIntroduce(profileDTO.getIntroduce());

        return uploadMsg;
    }

    public Map<String,String> uploadFile(boolean mainImg ,MultipartFile file) {
        Map<String,String> map = new HashMap<>();

        if(file.isEmpty()){
            throw new GlobalException(ErrorCode.WRONG_IMG);
        }

        String uploadPath = mainImg ? uploadDir : uploadDir + "/detail";

        File uploadFile = new File(uploadPath);
        if(!uploadFile.exists()){
            uploadFile.mkdirs();
        }

        String filename = uploadPath+"/"+file.getOriginalFilename();
        Path path = Paths.get(filename);

        try {
            Files.write(path,file.getBytes());

            // 파일의 형식과 크기를 가져오기
            String imgType = file.getContentType();
            long imgSize = file.getSize();

            // 이미지 형식 검사
            if (!imgType.equals("image/png") && !imgType.equals("image/jpeg")
            && !imgType.equals("image/gif") && !imgType.equals("image/jpg")){
                throw new GlobalException(ErrorCode.WRONG_IMG);
            }

            map.put("imgType",imgType);
            map.put("imgSize",Long.toString(imgSize));
            map.put("imgSrc",path.toString());

        }catch (IOException e){
            throw new GlobalException(ErrorCode.OVER_FILE);
        }

        log.info("저장된 이미지 경로 : ",path.toString());
        return map;
    }
}
