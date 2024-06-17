package web.mvc.service.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.dto.place.PlaceDetailImgDTO;
import web.mvc.dto.place.PlaceRecommendationDTO;
import web.mvc.entity.place.PlaceDetailImg;
import web.mvc.entity.place.PlaceRecommendation;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.place.PlaceDetailImgRepository;
import web.mvc.repository.place.PlaceRecommendationRepository;
import web.mvc.service.common.CommonService;
import web.mvc.service.place.PlaceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminPlaceServiceImpl implements AdminPlaceService{

    private final PlaceRecommendationRepository placeRecommendationRepository;
    private final PlaceDetailImgRepository placeDetailImgRepository;
    private final PlaceService placeService;
    private final CommonService commonService;

    @Value("${place.save.dir}")
    private String uploadDir;
    private String uploadMsg="등록에 성공했습니다.";
    private String alterMsg="수정에 성공했습니다.";


    @Override
    public List<PlaceRecommendationDTO> getPlaceList() {
        List<PlaceRecommendation> recommendations = placeRecommendationRepository.findAll();
        List<PlaceRecommendationDTO> list = new ArrayList<>();

        for (PlaceRecommendation recommendation : recommendations) {
            List<PlaceDetailImgDTO> detailImgDTOList = placeService.getPlaceDetailImg(recommendation.getPlaceSeq());

            PlaceRecommendationDTO dto = PlaceRecommendationDTO.builder()
                    .placeSeq(recommendation.getPlaceSeq())
                    .placeName(recommendation.getPlaceName())
                    .placeAddress(recommendation.getPlaceAddress())
                    .placeDescription(recommendation.getPlaceDescription())
                    .placeMainImg(recommendation.getPlaceMainImg())
                    .placeMainImgName(recommendation.getPlaceMainImgName())
                    .placeDetailImgList(detailImgDTOList)
                    .build();
            list.add(dto);
        }

        return list;
    }

    @Override
    public String uploadePlace(PlaceRecommendationDTO placeRecommendationDTO, MultipartFile file) {

        Map<String,String> map = commonService.uploadFile(true,file,uploadDir);

        PlaceRecommendation placeRecommendation = PlaceRecommendation.builder()
                        .placeName(placeRecommendationDTO.getPlaceName())
                        .placeAddress(placeRecommendationDTO.getPlaceAddress())
                        .placeDescription(placeRecommendationDTO.getPlaceDescription())
                        .placeMainImg(map.get("imgSrc"))
                        .placeMainImgSize(map.get("imgSize"))
                        .placeMainImgType(map.get("imgType"))
                        .placeMainImgName(map.get("imgName"))
                        .build();


            placeRecommendationRepository.save(placeRecommendation);

        return uploadMsg;
    }

    @Override
    public String uploadPlaceDetail(Long placeSeq,MultipartFile file) {

        Map<String,String> map = commonService.uploadFile(false,file,uploadDir);

        PlaceRecommendation placeRecommendation = placeRecommendationRepository.findById(placeSeq)
                .orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PLACE));

        PlaceDetailImg placeDetailImg = new PlaceDetailImg(placeRecommendation);

        placeDetailImg.setPlaceDetailImgSrc(map.get("imgSrc"));
        placeDetailImg.setPlaceDetailImgType(map.get("imgType"));
        placeDetailImg.setPlaceDetailImgSize(map.get("imgSize"));
        placeDetailImg.setPlaceDetailImgName(map.get("imgName"));

        placeDetailImgRepository.save(placeDetailImg);

        return uploadMsg;
    }

    @Override
    public String alterPlace(Long placeSeq, MultipartFile file, PlaceRecommendationDTO placeRecommendationDTO){

        PlaceRecommendation placeRecommendation = placeRecommendationRepository.findById(placeSeq).orElseThrow(
                ()->new GlobalException(ErrorCode.NOTFOUND_PLACE)
        );

        if(!file.isEmpty()){
            Map<String,String> map = commonService.uploadFile(true,file,uploadDir);
            placeRecommendation.setPlaceMainImg(map.get("imgSrc"));
            placeRecommendation.setPlaceMainImgSize(map.get("imgSize"));
            placeRecommendation.setPlaceMainImgType(map.get("imgType"));
        }

        placeRecommendation.setPlaceName(placeRecommendationDTO.getPlaceName());
        placeRecommendation.setPlaceAddress(placeRecommendationDTO.getPlaceAddress());
        placeRecommendation.setPlaceDescription(placeRecommendationDTO.getPlaceDescription());


        return alterMsg;
    }

    @Override
    public String alterPlaceDetail(Long placeDetailImgSeq, MultipartFile file, PlaceDetailImgDTO placeDetailImgDTO) {
        PlaceDetailImg placeDetailImg = placeDetailImgRepository.findById(placeDetailImgSeq).orElseThrow(
                ()->new GlobalException(ErrorCode.NOTFOUND_PLACE)
        );

        if(!file.isEmpty()){
            Map<String,String> map = commonService.uploadFile(false,file,uploadDir);
            placeDetailImg.setPlaceDetailImgSrc(map.get("imgSrc"));
            placeDetailImg.setPlaceDetailImgType(map.get("imgType"));
            placeDetailImg.setPlaceDetailImgSize(map.get("imgSize"));
        }

        return alterMsg;
    }


}
