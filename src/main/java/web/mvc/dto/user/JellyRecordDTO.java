package web.mvc.dto.user;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JellyRecordDTO {

    private Long jellyRecordSeq;
    private int jellyRecCategory;
    private String jellyRecord;
    private LocalDateTime jellyRegDate;

}
