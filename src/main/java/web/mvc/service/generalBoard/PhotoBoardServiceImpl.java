package web.mvc.service.generalBoard;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import web.mvc.dto.generalBoard.PhotoBoardDTO;
import web.mvc.entity.generalBoard.PhotoBoard;
import web.mvc.repository.generalBoard.PhotoBoardRepository;
import web.mvc.repository.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PhotoBoardServiceImpl implements PhotoBoardService {

    private final PhotoBoardRepository photoBoardRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public PhotoBoardDTO createPhotoBoard(PhotoBoardDTO photoBoardDTO) {
        PhotoBoard photoBoard = modelMapper.map(photoBoardDTO, PhotoBoard.class);
        photoBoard.setUser(userRepository.findById(photoBoardDTO.getUserSeq())
                .orElseThrow(() -> new RuntimeException("에러발생. exception 추후 수정")));
        photoBoard = photoBoardRepository.save(photoBoard);
        return modelMapper.map(photoBoard, PhotoBoardDTO.class);
    }

    @Override
    public PhotoBoardDTO getPhotoBoardById(Long photoBoardSeq) {
        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq)
                .orElseThrow(() -> new RuntimeException("에러발생. exception 추후 수정"));
        return modelMapper.map(photoBoard, PhotoBoardDTO.class);
    }

    @Override
    public List<PhotoBoardDTO> getAllPhotoBoards() {
        return photoBoardRepository.findAll().stream()
                .map(board -> modelMapper.map(board, PhotoBoardDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PhotoBoardDTO updatePhotoBoard(Long photoBoardSeq, PhotoBoardDTO photoBoardDTO) {
        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq)
                .orElseThrow(() -> new RuntimeException("에러발생. exception 추후 수정"));

        modelMapper.map(photoBoardDTO, photoBoard); //ModelMapper를 사용하여 photoBoardDTO의 값을 기존의 photoBoard 엔티티에 매핑
        photoBoard = photoBoardRepository.save(photoBoard);
        return modelMapper.map(photoBoard, PhotoBoardDTO.class);    //최종적으로 업데이트된 PhotoBoard 엔티티를 PhotoBoardDTO로 변환하여 반환
    }

    @Override
    public void deletePhotoBoard(Long photoBoardSeq) {
        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq)
                .orElseThrow(() -> new RuntimeException("에러발생. exception 추후 수정"));
        photoBoardRepository.delete(photoBoard);
    }
}
