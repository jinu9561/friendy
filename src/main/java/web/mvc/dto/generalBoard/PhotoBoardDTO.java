package web.mvc.dto.generalBoard;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import web.mvc.entity.generalBoard.PhotoBoard;

import web.mvc.entity.user.Users;

import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@ToString
@AllArgsConstructor // 모든 멤버를 인자(parameter)로 받아 DTO 객체를 생성
@NoArgsConstructor
@Builder
public class PhotoBoardDTO {

    private Long photoBoardSeq;
    private Long userSeq;
    private String photoBoardTitle;
    private String photoBoardMainImgName;
    private Long interestSeq;
    private String photoBoardPwd;
    private int photoBoardLike;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") //Json으로 보낼 때, LocalDateTime을 String으로 변환
    private LocalDateTime photoBoardRegDate;    //자동변환
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime photoBoardUpdateDate; //자동변환
    private int photoBoardCount;



    // 사용자가 보낸 DTO에서 엔티티로 변환하는 메서드
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


    // 엔티티에서 사용자에게 보낼 DTO로 변환하는 정적 메서드
    public static PhotoBoardDTO fromPhotoBoardEntity(PhotoBoard photoBoard) {
        return PhotoBoardDTO.builder()
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
                .build();
    }
}