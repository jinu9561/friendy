package web.mvc.service.admin;

import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.place.PlaceDetailImgDTO;
import web.mvc.dto.place.PlaceRecommendationDTO;

import java.util.List;
import java.util.Optional;

public interface AdminPlaceService {

    // 전체 장소 조회
    public List<PlaceRecommendationDTO> getPlaceList();

    // 장소 등록
    public String uploadePlace(PlaceRecommendationDTO placeRecommendationDTO, MultipartFile file);

    // 장소에대한 디테일 정보 등록
    public String uploadPlaceDetail(Long placeSeq,MultipartFile file);


    // 장소 수정
    public String alterPlace(Long placeSeq, MultipartFile file,PlaceRecommendationDTO placeRecommendationDTO);

    // 장소에대한 디테일 정보 수정
    public String alterPlaceDetail(Long placeDetailImgSeq, MultipartFile file, PlaceDetailImgDTO placeDetailImgDTO);
}
