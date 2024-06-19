package web.mvc.dto.generalBoard;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Resource;
import lombok.*;
import web.mvc.entity.generalBoard.PhotoBoard;
import web.mvc.entity.user.Users;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.time.LocalDateTime;


@Setter
@Getter
@ToString
@AllArgsConstructor // 모든 멤버를 인자(parameter)로 받아 DTO 객체를 생성
@NoArgsConstructor
@Builder
public class PhotoBoardDTO {

    private Long photoBoardSeq;
    private Long userSeq;
    private String nickName;
    private String photoBoardTitle;
    private String photoMainImgSrc;
    private String photoMainImgType;
    private String photoMainImgSize;
    private org.springframework.core.io.Resource photoResource; //프론트로 보낼 때만 사용. 이미지경로를 String에서 Resource로 변환
    private Long interestSeq;
    private String photoBoardPwd;
    private int photoBoardLike;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") //Json으로 보낼 때, LocalDateTime을 String으로 변환
    private LocalDateTime photoBoardRegDate;    //자동변환
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime photoBoardUpdateDate; //자동변환

    private int PhotoBoardCount;

    // 사용자가 보낸 DTO에서 엔티티로 변환하는 메서드
    public PhotoBoard toPhotoBoardEntity(PhotoBoardDTO photoBoardDTO, Users user) {
        return PhotoBoard.builder()
                .user(user)
                .photoBoardTitle(photoBoardDTO.getPhotoBoardTitle())
                .photoMainImgSrc(photoBoardDTO.getPhotoMainImgSrc())
                .photoMainImgType(photoBoardDTO.getPhotoMainImgType())
                .photoMainImgSize(photoBoardDTO.getPhotoMainImgSize())
                .interestSeq(photoBoardDTO.getInterestSeq())
                .photoBoardPwd(photoBoardDTO.getPhotoBoardPwd())
                .photoBoardLike(photoBoardDTO.getPhotoBoardLike())
                .build();
    }

    // 엔티티에서 사용자에게 보낼 DTO로 변환하는 정적 메서드
    public static PhotoBoardDTO fromPhotoBoardEntity(PhotoBoard photoBoard) {
        return new PhotoBoardDTO(
                photoBoard.getPhotoBoardSeq(),
                photoBoard.getUser().getUserSeq(),
                photoBoard.getUser().getNickName(),
                photoBoard.getPhotoBoardTitle(),
                photoBoard.getPhotoMainImgSrc(),
                photoBoard.getPhotoMainImgType(),
                photoBoard.getPhotoMainImgSize(),
                new DefaultResourceLoader().getResource(photoBoard.getPhotoMainImgSrc()),
                photoBoard.getInterestSeq(),
                photoBoard.getPhotoBoardPwd(),
                photoBoard.getPhotoBoardLike(),
                photoBoard.getPhotoBoardRegDate(),
                photoBoard.getPhotoBoardUpdateDate(),
                photoBoard.getPhotoBoardCount());
    }

    // String을 Resource로 변환하는 메서드
    public void setPhotoResource(String photoImgSrc) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        this.photoResource = resourceLoader.getResource(photoImgSrc);
    }
}