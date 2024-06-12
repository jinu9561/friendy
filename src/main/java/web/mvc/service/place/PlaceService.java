package web.mvc.service.place;

import web.mvc.dto.place.PlaceDetailImgDTO;
import web.mvc.dto.place.PlaceRecommendationDTO;


import java.util.List;

public interface PlaceService {

    // 유저 주소에 맞는 장소 조회 -> 4개만
    public List<PlaceRecommendationDTO> getPlaceByUserAddress(String address);


    // 장소에 대한 디테일 사진 가져오기
    public List<PlaceDetailImgDTO> getPlaceDetailImg(Long placeSeq);

}
