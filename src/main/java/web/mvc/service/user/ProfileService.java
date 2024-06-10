package web.mvc.service.user;

import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.user.ProfileDTO;

import java.util.Map;
import java.util.Objects;

public interface ProfileService {

    // 프로필 정보 띄우기
    public Map<String, Object> getProfile(Long userSeq);

    // 프로필 메인 사진 업로드
    public String uploadMainPicture(Long userSeq,MultipartFile file);

    // 프로필에 세부 사진 업로드
    public String uploadDetail(Long userSeq,MultipartFile file);

    // 프로필 자기소개 관심사 수정
    public String alterProfile(Long userSeq, ProfileDTO profileDTO);

}
