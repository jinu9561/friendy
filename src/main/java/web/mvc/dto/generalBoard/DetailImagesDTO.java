package web.mvc.dto.generalBoard;


import lombok.*;
import web.mvc.entity.generalBoard.PhotoBoard;
import web.mvc.entity.generalBoard.DetailImages;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DetailImagesDTO {
    private Long photoBoardDetailImgSeq;
    private String photoBoardDetailImgName;
    private PhotoBoard photoBoard;

    public DetailImages toPhotoBoardDetailImgEntity(PhotoBoard photoBoard) {
        return DetailImages.builder()
                .photoBoardDetailImgName(this.photoBoardDetailImgName)
                .photoBoard(photoBoard)
                .build();
    }

    public static DetailImagesDTO fromPhotoBoardDetailImgEntity(DetailImages detailImages) {
        return DetailImagesDTO.builder()
                .photoBoardDetailImgSeq(detailImages.getPhotoBoardDetailImgSeq())
                .photoBoardDetailImgName(detailImages.getPhotoBoardDetailImgName())
                .photoBoard(detailImages.getPhotoBoard())
                .build();
    }
}


