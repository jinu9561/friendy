package web.mvc.dto.user;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProFileDetailImgDTO {

    private Long profileDetailImgSeq;
    private String profileDetailImgSrc;
    private String profileDetailImgType;
    private String profileDetailImgSize;

}
