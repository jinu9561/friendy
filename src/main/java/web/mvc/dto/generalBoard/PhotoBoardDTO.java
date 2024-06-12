package web.mvc.dto.generalBoard;

import lombok.*;
import web.mvc.entity.generalBoard.PhotoBoard;
import web.mvc.entity.user.Users;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor // 모든 멤버를 인자(parameter)로 받아 DTO 객체를 생성
@NoArgsConstructor
public class PhotoBoardDTO {

    private Long photoBoardSeq;
    private Long userSeq;
    private String photoBoardTitle;
    private String photoImgSrc;
    private Long interestSeq;
    private String photoBoardPwd;
    private int photoBoardLike;

    // 사용자가 보낸 DTO에서 엔티티로 변환하는 메서드
    public PhotoBoard toPhotoBoardEntity(PhotoBoardDTO photoBoardDTO) {
        return PhotoBoard.builder()
                .photoBoardSeq(this.photoBoardSeq)
                .user(Users.builder().userSeq(photoBoardDTO.getUserSeq()).build())
                .photoBoardTitle(this.photoBoardTitle)
                .photoImgSrc(this.photoImgSrc)
                .interestSeq(this.interestSeq)
                .photoBoardPwd(this.photoBoardPwd)
                .photoBoardLike(this.photoBoardLike)
                .build();
    }

    // 엔티티에서 사용자에게 보낼 DTO로 변환하는 정적 메서드
    public static PhotoBoardDTO fromPhotoBoardEntity(PhotoBoard photoBoard) {
        return new PhotoBoardDTO(
                photoBoard.getPhotoBoardSeq(),
                photoBoard.getUser().getUserSeq(),
                photoBoard.getPhotoBoardTitle(),
                photoBoard.getPhotoImgSrc(),
                photoBoard.getInterestSeq(),
                photoBoard.getPhotoBoardPwd(),
                photoBoard.getPhotoBoardLike()
        );
    }
}