package web.mvc.dto.user;


import lombok.*;
import web.mvc.domain.user.Users;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    private Long profileSeq;
    private String profileMainImg;
    private String introduce;
    private Users user;

}
