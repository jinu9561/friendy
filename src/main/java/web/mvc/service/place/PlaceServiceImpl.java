package web.mvc.service.place;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import web.mvc.dto.place.PlaceDetailImgDTO;
import web.mvc.dto.place.PlaceRecommendationDTO;
import web.mvc.entity.place.PlaceDetailImg;
import web.mvc.entity.place.PlaceRecommendation;
import web.mvc.repository.place.PlaceDetailImgRepository;
import web.mvc.repository.place.PlaceRecommendationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRecommendationRepository placeRecommendationRepository;
    private final PlaceDetailImgRepository placeDetailImgRepository;

    private int maxSize = 4;
    private String defaultAddress = "경기도";

    @Override
    public List<PlaceRecommendationDTO> getPlaceByUserAddress(String address) {
        log.info("getPlaceByUserAddress, address: {}", address);
        if(address.isEmpty() || address == null){
            address = defaultAddress;
        }

        List<PlaceRecommendation> placeRecommendationList = placeRecommendationRepository.findByPlaceAddress(address);
        log.info(placeRecommendationList.toString());
        List<PlaceRecommendationDTO> placeRecommendationDTOList = new ArrayList<>();

        for (PlaceRecommendation placeRecommendation : placeRecommendationList) {
            List<PlaceDetailImgDTO> list = this.getPlaceDetailImg(placeRecommendation.getPlaceSeq());

            PlaceRecommendationDTO placeRecommendationDTO = PlaceRecommendationDTO.builder()
                    .placeSeq(placeRecommendation.getPlaceSeq())
                    .placeName(placeRecommendation.getPlaceName())
                    .placeAddress(placeRecommendation.getPlaceAddress())
                    .placeDescription(placeRecommendation.getPlaceDescription())
                    .placeMainImg(placeRecommendation.getPlaceMainImg())
                    .placeDetailImgList(list)
                    .build();

            if(placeRecommendationDTOList.size() < maxSize){
                placeRecommendationDTOList.add(placeRecommendationDTO);
            }

        }

        return placeRecommendationDTOList;
    }

    @Override
    public List<PlaceDetailImgDTO> getPlaceDetailImg(Long placeSeq) {
        List<PlaceDetailImgDTO> placeDetailImgDTOList = new ArrayList<>();
        List<PlaceDetailImg> placeDetailImgList = placeDetailImgRepository.findByPlaceSeq(placeSeq);

        for (PlaceDetailImg placeDetailImg : placeDetailImgList) {
            PlaceDetailImgDTO dto = PlaceDetailImgDTO.builder()
                    .placeDetailImgSeq(placeDetailImg.getPlaceDetailImgSeq())
                    .placeDetailImgSrc(placeDetailImg.getPlaceDetailImgSrc())
                    .placeDetailImgType(placeDetailImg.getPlaceDetailImgType())
                    .placeDetailImgSize(placeDetailImg.getPlaceDetailImgSize())
                    .build();
            placeDetailImgDTOList.add(dto);
        }
        return placeDetailImgDTOList;
    }
}
