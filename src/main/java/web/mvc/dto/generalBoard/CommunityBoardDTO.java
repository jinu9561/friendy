package web.mvc.dto.generalBoard;

import lombok.*;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.entity.user.Users;
import web.mvc.repository.user.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor //모든 멤버를 인자(parameter)로 받아 DTO객체를 생성
@NoArgsConstructor
@Builder
public class CommunityBoardDTO {
    private UserRepository userRepository;

    private Long commBoardSeq; //게시글 번호
    private Long userSeq; //게시글 작성자
    private String nickName; //게시글 작성자 닉네임
    private String boardTitle; //게시글 제목
    private String boardContent; //게시글 내용
    private int boardType;  //0이 자유게시판, 1이 익명게시판
    private int boardLike; //추천수
    private String boardPwd;    //게시글 비밀번호
    private LocalDateTime boardRegDate; //게시글 등록일
    private LocalDateTime boardUpdateDate; //게시글 수정일
    private int commBoardCount; //조회수

    // 사용자가 보낸 DTO에서 엔티티로 변환하는 메서드
    public CommunityBoard toEntity() {
        return CommunityBoard.builder()
                .user(userRepository.findById(this.userSeq).orElseThrow(() -> new RuntimeException("User not found with userid: " + userSeq)))
                .boardTitle(this.boardTitle)//
                .boardContent(this.boardContent)
                .boardType(this.boardType)
                .boardLike(this.boardLike)
                .boardPwd(this.boardPwd)
                .build();
    }

}

