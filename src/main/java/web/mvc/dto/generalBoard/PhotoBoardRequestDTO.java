package web.mvc.dto.generalBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoBoardRequestDTO {
    private PhotoBoardDTO photoBoardDTO;
    private List<DetailImagesDTO> detailImagesDTOS;
}
