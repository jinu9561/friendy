package web.mvc.service.generalBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional(readOnly=true)
    @Override
    public List<PhotoBoardDTO> getAllPhotoBoards() {
        log.info("Fetching all photo boards");
        List<PhotoBoard> photoBoards = photoBoardRepository.findAll();

        if (photoBoards == null || photoBoards.isEmpty()) {
            log.warn("사진이 없습니다.");
            throw new RuntimeException("사진이 없습니다.");
        }

        List<PhotoBoardDTO> photoBoardDTOs = photoBoards.stream()
                .map(PhotoBoardDTO::fromPhotoBoardEntity)
                .collect(Collectors.toList());

        log.info("Fetched {} photo boards", photoBoardDTOs.size());
        return photoBoardDTOs;
    }

    @Transactional
    @Override
    public PhotoBoardDTO createPhotoBoard(PhotoBoardDTO photoBoardDTO) {
        log.info("Creating PhotoBoard with title: {}", photoBoardDTO.getPhotoBoardTitle());

        // 사용자 엔티티 조회
        Users user = userRepository.findById(photoBoardDTO.getUserSeq())
                .orElseThrow(() -> new RuntimeException("User not found with userid: " + photoBoardDTO.getUserSeq()));

        // 조회된 사용자 객체를 변환 메서드에 전달하여 PhotoBoard 엔티티 생성
        PhotoBoard photoBoard = photoBoardDTO.toPhotoBoardEntity(photoBoardDTO, user);

        // PhotoBoard 엔티티 저장
        PhotoBoard savedPhotoBoard = photoBoardRepository.save(photoBoard);
        log.info("PhotoBoard created with SEQ: {}", savedPhotoBoard.getPhotoBoardSeq());

        // 저장된 PhotoBoard 엔티티를 DTO로 변환하여 반환
        PhotoBoardDTO createdPhotoBoardDTO = PhotoBoardDTO.fromPhotoBoardEntity(savedPhotoBoard);
        log.info("PhotoBoardDTO created with SEQ: {}", createdPhotoBoardDTO.getPhotoBoardSeq());
        return createdPhotoBoardDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public PhotoBoardDTO getPhotoBoardById(Long photoBoardSeq) {
        log.info("Fetching photo board with SEQ: {}", photoBoardSeq);

        // PhotoBoard 엔티티 조회
        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq)
                .orElseThrow(() -> new RuntimeException("PhotoBoard not found with seq: " + photoBoardSeq));

        // 엔티티를 DTO로 변환하여 반환
        PhotoBoardDTO photoBoardDTO = PhotoBoardDTO.fromPhotoBoardEntity(photoBoard);
        log.info("Fetched photo board with SEQ: {}", photoBoardDTO.getPhotoBoardSeq());
        return photoBoardDTO;
    }

    @Transactional
    @Override
    public PhotoBoardDTO updatePhotoBoard(PhotoBoardDTO photoBoardDTO) {
        long photoBoardSeq = photoBoardDTO.getPhotoBoardSeq();
        log.info("Updating photo board with SEQ: {}", photoBoardSeq);

        // 기존 PhotoBoard 엔티티 조회
        PhotoBoard existingPhotoBoard = photoBoardRepository.findById(photoBoardSeq)
                .orElseThrow(() -> new RuntimeException("PhotoBoard not found with seq: " + photoBoardSeq));

        // 업데이트할 사용자 엔티티 조회
        Users user = userRepository.findById(photoBoardDTO.getUserSeq())
                .orElseThrow(() -> new RuntimeException("User not found with userid: " + photoBoardDTO.getUserSeq()));

        // 엔티티 필드 업데이트
        existingPhotoBoard.setPhotoBoardTitle(photoBoardDTO.getPhotoBoardTitle());
        existingPhotoBoard.setPhotoImgSrc(photoBoardDTO.getPhotoImgSrc());
        existingPhotoBoard.setInterestSeq(photoBoardDTO.getInterestSeq());
        existingPhotoBoard.setPhotoBoardPwd(photoBoardDTO.getPhotoBoardPwd());
        existingPhotoBoard.setPhotoBoardLike(photoBoardDTO.getPhotoBoardLike());
        existingPhotoBoard.setUser(user);

        // 엔티티 저장
        PhotoBoard updatedPhotoBoard = photoBoardRepository.save(existingPhotoBoard);
        log.info("PhotoBoard updated with SEQ: {}", updatedPhotoBoard.getPhotoBoardSeq());

        // 저장된 엔티티를 DTO로 변환하여 반환
        PhotoBoardDTO updatedPhotoBoardDTO = PhotoBoardDTO.fromPhotoBoardEntity(updatedPhotoBoard);
        return updatedPhotoBoardDTO;
    }

    @Transactional
    @Override
    public String deletePhotoBoard(Long photoBoardSeq) {
        log.info("Deleting photo board with SEQ: {}", photoBoardSeq);

        // 삭제하려는 PhotoBoard 엔티티 조회
        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq)
                .orElseThrow(() -> new RuntimeException("PhotoBoard not found with seq: " + photoBoardSeq));

        // PhotoBoard 엔티티 삭제
        photoBoardRepository.delete(photoBoard);
        log.info("PhotoBoard deleted with SEQ: {}", photoBoardSeq);

        String message = "PhotoBoard deleted successfully";
        log.info(message);
        return message;
    }
}
