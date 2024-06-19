package web.mvc.dto.generalBoard;

import jakarta.persistence.*;
import lombok.*;
import web.mvc.entity.generalBoard.PhotoBoard;
import web.mvc.entity.generalBoard.PhotoBoardDetailImg;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PhotoBoardDetailImgDTO {
    private Long photoBoardDetailImgSeq;
    private String photoBoardDetailImgName;
    private String photoBoardDetailImgSrc;
    private String photoBoardDetailImgType;
    private String photoBoardDetailImgSize;
    private PhotoBoard photoBoard;

    public PhotoBoardDetailImgDTO toPhotoBoardDetailImgEntity(PhotoBoardDetailImgDTO photoBoardDetailImgDTO, PhotoBoard photoBoard) {
        return PhotoBoardDetailImgDTO.builder()
                .photoBoardDetailImgName(photoBoardDetailImgDTO.getPhotoBoardDetailImgName())
                .photoBoardDetailImgSrc(photoBoardDetailImgDTO.getPhotoBoardDetailImgSrc())
                .photoBoardDetailImgType(photoBoardDetailImgDTO.getPhotoBoardDetailImgType())
                .photoBoardDetailImgSize(photoBoardDetailImgDTO.getPhotoBoardDetailImgSize())
                .photoBoard(photoBoard)
                .build();
    }

    public static PhotoBoardDetailImgDTO fromPhotoBoardDetailImgEntity(PhotoBoardDetailImg photoBoardDetailImg) {
        return PhotoBoardDetailImgDTO.builder()
                .photoBoardDetailImgSeq(photoBoardDetailImg.getPhotoBoardDetailImgSeq())
                .photoBoardDetailImgName(photoBoardDetailImg.getPhotoBoardDetailImgName())
                .photoBoardDetailImgSrc(photoBoardDetailImg.getPhotoBoardDetailImgSrc())
                .photoBoardDetailImgType(photoBoardDetailImg.getPhotoBoardDetailImgType())
                .photoBoardDetailImgSize(photoBoardDetailImg.getPhotoBoardDetailImgSize())
                .photoBoard(photoBoardDetailImg.getPhotoBoard())
                .build();
    }
}


