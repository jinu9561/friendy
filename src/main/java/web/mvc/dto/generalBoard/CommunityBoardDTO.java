package web.mvc.dto.generalBoard;

import lombok.*;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.entity.user.Users;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor //모든 멤버를 인자(parameter)로 받아 DTO객체를 생성
@NoArgsConstructor
public class CommunityBoardDTO {

    private Long commBoardSeq;
    private Long userSeq;
    private String boardTitle;
    private String boardContent;
    private int boardType;  //0이 자유게시판, 1이 익명게시판
    private int boardLike;
    private String boardPwd;
    private LocalDateTime boardRegDate;
    private LocalDateTime boardUpdateDate;

    // 사용자가 보낸 DTO에서 엔티티로 변환하는 메서드
    public CommunityBoard toCommunityBoardEntity(CommunityBoardDTO communityBoardDTO, Users user) {
        return CommunityBoard.builder()
                .user(user)  // 조회된 사용자 객체를 설정
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardType(this.boardType)
                .boardLike(this.boardLike)
                .boardPwd(this.boardPwd)
                .build();
    }

    // 엔티티에서 사용자에게 보낼 DTO로 변환하는 정적 메서드
    public static CommunityBoardDTO fromCommunityBoardEntity(CommunityBoard communityBoard) {
        return new CommunityBoardDTO(
                communityBoard.getCommBoardSeq(),
                communityBoard.getUser().getUserSeq(),
                communityBoard.getBoardTitle(),
                communityBoard.getBoardContent(),
                communityBoard.getBoardType(),
                communityBoard.getBoardLike(),
                communityBoard.getBoardPwd(),
                communityBoard.getBoardRegDate(),
                communityBoard.getBoardUpdateDate()
        );
    }
}

