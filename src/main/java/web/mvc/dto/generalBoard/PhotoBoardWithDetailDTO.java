package web.mvc.dto.generalBoard;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import web.mvc.entity.generalBoard.PhotoBoard;
import web.mvc.entity.user.Users;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoBoardWithDetailDTO {

    private Long photoBoardSeq;
    private Long userSeq;
    private String photoBoardTitle;
    private String photoBoardMainImgName;
    private Long interestSeq;
    private String photoBoardPwd;
    private int photoBoardLike;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime photoBoardRegDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime photoBoardUpdateDate;
    private int photoBoardCount;
    private List<DetailImagesDTO> detailImagesDTOS;

    public PhotoBoard toPhotoBoardEntity(Users user) {
        return PhotoBoard.builder()
                .user(user)
                .photoBoardTitle(this.photoBoardTitle)
                .photoBoardMainImgName(this.photoBoardMainImgName)
                .interestSeq(this.interestSeq)
                .photoBoardPwd(this.photoBoardPwd)
                .photoBoardLike(this.photoBoardLike)
                .photoBoardCount(this.photoBoardCount)
                .build();
    }

    public static PhotoBoardWithDetailDTO fromPhotoBoardEntity(PhotoBoard photoBoard) {
        return PhotoBoardWithDetailDTO.builder()
                .photoBoardSeq(photoBoard.getPhotoBoardSeq())
                .userSeq(photoBoard.getUser().getUserSeq())
                .photoBoardTitle(photoBoard.getPhotoBoardTitle())
                .photoBoardMainImgName(photoBoard.getPhotoBoardMainImgName())
                .interestSeq(photoBoard.getInterestSeq())
                .photoBoardPwd(photoBoard.getPhotoBoardPwd())
                .photoBoardLike(photoBoard.getPhotoBoardLike())
                .photoBoardRegDate(photoBoard.getPhotoBoardRegDate())
                .photoBoardUpdateDate(photoBoard.getPhotoBoardUpdateDate())
                .photoBoardCount(photoBoard.getPhotoBoardCount())
                .detailImagesDTOS(photoBoard.getDetailImages().stream()
                        .map(DetailImagesDTO::fromPhotoBoardDetailImgEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
