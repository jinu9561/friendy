package web.mvc.service.generalBoard;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.dto.generalBoard.PhotoBoardDTO;
import web.mvc.entity.generalBoard.PhotoBoard;
import web.mvc.entity.user.Users;
import web.mvc.repository.generalBoard.PhotoBoardRepository;
import web.mvc.repository.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoBoardServiceImpl implements PhotoBoardService {

    private final PhotoBoardRepository photoBoardRepository;
    private final UserRepository userRepository;


    /*Transactional이 적용된 메서드나 클래스는 트랜잭션 내에서 실행.
    모든 데이터베이스 작업이 성공적으로 완료되면 트랜잭션이 커밋되고, 그렇지 않으면 롤백*/
    @Transactional(readOnly=true)
    @Override
    public List<PhotoBoardDTO> getAllPhotoBoards() {
        log.info("Fetching all photo boards");
        List<PhotoBoard> photoBoards = photoBoardRepository.findAll();

        if (photoBoards == null || photoBoards.isEmpty()) {
            log.warn("사진이 없습니다.");
            throw new RuntimeException("사진이 없습니다.");
        }

        return photoBoards.stream()
                .map(PhotoBoardDTO::fromPhotoBoardEntity)
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public PhotoBoardDTO createPhotoBoard(PhotoBoardDTO photoBoardDTO) {
        log.info("PhotoBoardDTOseq = {}", photoBoardDTO.getPhotoBoardSeq());
        PhotoBoard photoBoard = photoBoardDTO.toPhotoBoardEntity(photoBoardDTO);
        PhotoBoard savedPhotoBoard = photoBoardRepository.save(photoBoard);
        log.info("PhotoBoard created with ID: {}", savedPhotoBoard.getPhotoBoardSeq());
        return PhotoBoardDTO.fromPhotoBoardEntity(savedPhotoBoard);

    }

//    @Transactional
//    @Override
//    public PhotoBoardDTO getPhotoBoardById(Long photoBoardSeq) {
//        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq)
//                .orElseThrow(() -> new RuntimeException("에러발생. exception 추후 수정"));
//        return modelMapper.map(photoBoard, PhotoBoardDTO.class);
//    }
//
//    @Transactional
//    @Override
//    public PhotoBoardDTO updatePhotoBoard(Long photoBoardSeq, PhotoBoardDTO photoBoardDTO) {
//        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq)
//                .orElseThrow(() -> new RuntimeException("에러발생. exception 추후 수정"));
//
//        modelMapper.map(photoBoardDTO, photoBoard); //ModelMapper를 사용하여 photoBoardDTO의 값을 기존의 photoBoard 엔티티에 매핑
//        photoBoard = photoBoardRepository.save(photoBoard);
//        return modelMapper.map(photoBoard, PhotoBoardDTO.class);    //최종적으로 업데이트된 PhotoBoard 엔티티를 PhotoBoardDTO로 변환하여 반환
//    }
//
//    @Transactional
//    @Override
//    public void deletePhotoBoard(Long photoBoardSeq) {
//        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq)
//                .orElseThrow(() -> new RuntimeException("에러발생. exception 추후 수정"));
//        photoBoardRepository.delete(photoBoard);
//    }
}
