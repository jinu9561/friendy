package web.mvc.service.generalBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.dto.generalBoard.PhotoBoardDTO;
import web.mvc.dto.generalBoard.DetailImagesDTO;
import web.mvc.dto.generalBoard.PhotoBoardWithDetailDTO;
import web.mvc.entity.generalBoard.DetailImages;
import web.mvc.entity.generalBoard.PhotoBoard;
import web.mvc.entity.user.Users;
import web.mvc.repository.generalBoard.PhotoBoardDetailImgRepository;
import web.mvc.repository.generalBoard.PhotoBoardRepository;
import web.mvc.repository.user.UserRepository;


import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoBoardServiceImpl implements PhotoBoardService {

    //생성자가 하나 뿐이라면 @Autowired 어노테이션 생략 가능
    @Autowired
    private final PhotoBoardRepository photoBoardRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PhotoBoardDetailImgRepository photoBoardDetailImgRepository;

    @Value("${photo.save.dir}")
    private String uploadDir;

    //모든 사진게시물을 가져오는 메서드
    @Transactional(readOnly = true)
    @Override
    public List<PhotoBoardDTO> getAllPhotoBoards() {
        log.info("Fetching all photo boards");
        List<PhotoBoard> photoBoards = photoBoardRepository.findAll();

        if (photoBoards == null || photoBoards.isEmpty()) {
            log.warn("사진게시물이 없습니다.");
            throw new RuntimeException("사진게시물이 없습니다.");
        }

        List<PhotoBoardDTO> photoBoardDTOs = photoBoards.stream()
                .map(PhotoBoardDTO::fromPhotoBoardEntity)
                .collect(Collectors.toList());

        log.info("Fetched {} photo boards", photoBoardDTOs.size());
        return photoBoardDTOs;
    }

    //사진게시물을 생성하는 메서드
    @Override
    @Transactional
    public PhotoBoardDTO createPhotoBoard(PhotoBoardDTO photoBoardDTO, List<DetailImagesDTO> detailImagesDTOS) {
        // 유저 엔티티 조회
        Users user = userRepository.findById(photoBoardDTO.getUserSeq())
                .orElseThrow(() -> new IllegalArgumentException("유저 아이디가 없음."));

        log.info("Creating PhotoBoard with title: {}", photoBoardDTO.getPhotoBoardTitle());
        // PhotoBoard 엔티티 생성 및 저장
        final PhotoBoard photoBoard = photoBoardDTO.toPhotoBoardEntity(user);
        final PhotoBoard savedPhotoBoard = photoBoardRepository.save(photoBoard);

        // DetailImages 엔티티 생성 및 저장
        List<DetailImages> detailImages = detailImagesDTOS.stream()
                .map(dto -> dto.toPhotoBoardDetailImgEntity(savedPhotoBoard))
                .collect(Collectors.toList());
        photoBoardDetailImgRepository.saveAll(detailImages);

        log.info("PhotoBoard created with title: {}", savedPhotoBoard.getPhotoBoardTitle());
        // 결과 DTO 반환
        return PhotoBoardDTO.fromPhotoBoardEntity(savedPhotoBoard);
    }

    @Transactional(readOnly = true)
    @Override
    public PhotoBoardWithDetailDTO getPhotoBoardById(Long photoBoardSeq)  {
        log.info("Fetching photo board with SEQ: {}", photoBoardSeq);

        // PhotoBoard 엔티티 조회
        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq)
                .orElseThrow(() -> new RuntimeException("PhotoBoard not found with seq: " + photoBoardSeq));

        // 엔티티를 DTO로 변환하여 반환
        PhotoBoardWithDetailDTO photoBoardWithDetailDTO = PhotoBoardWithDetailDTO.fromPhotoBoardEntity(photoBoard);
        log.info("Fetched photo board with SEQ: {}", photoBoardWithDetailDTO.getPhotoBoardSeq());
        return photoBoardWithDetailDTO;
    }

    @Override
    @Transactional
    public PhotoBoardDTO updatePhotoBoard(Long seq, PhotoBoardDTO photoBoardDTO, List<DetailImagesDTO> detailImagesDTOS) {
        log.info("Updating PhotoBoard with seq: {}", seq);
        PhotoBoard photoBoard = photoBoardRepository.findById(seq)
                .orElseThrow(() -> new IllegalArgumentException("사진게시물이 존재하지 않습니다."));

        Users user = userRepository.findById(photoBoardDTO.getUserSeq())
                .orElseThrow(() -> new IllegalArgumentException("유저 아이디가 없음."));

        // 엔티티 필드 업데이트
        photoBoard.setPhotoBoardTitle(photoBoardDTO.getPhotoBoardTitle());
        photoBoard.setPhotoBoardMainImgName(photoBoardDTO.getPhotoBoardMainImgName());
        photoBoard.setInterestSeq(photoBoardDTO.getInterestSeq());
        photoBoard.setPhotoBoardPwd(photoBoardDTO.getPhotoBoardPwd());
        photoBoard.setPhotoBoardLike(photoBoardDTO.getPhotoBoardLike());
        photoBoard.setPhotoBoardCount(photoBoardDTO.getPhotoBoardCount());

        // 엔티티 저장
        final PhotoBoard updatedPhotoBoard = photoBoardRepository.save(photoBoard);
        // 기존의 사진 삭제
        photoBoardDetailImgRepository.deleteByPhotoBoard(photoBoard);

        // 새로운 사진 추가
        List<DetailImages> detailImages = detailImagesDTOS.stream()
                .map(dto -> dto.toPhotoBoardDetailImgEntity(updatedPhotoBoard))
                .collect(Collectors.toList());
        photoBoardDetailImgRepository.saveAll(detailImages);

        log.info("PhotoBoard updated with seq: {}", seq);
        return PhotoBoardDTO.fromPhotoBoardEntity(updatedPhotoBoard);
    }

        @Transactional
        @Override
        public String deletePhotoBoard (Long photoBoardSeq){
            log.info("Deleting photo board with SEQ: {}", photoBoardSeq);

            // 삭제하려는 PhotoBoard 엔티티 조회
            PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq)
                    .orElseThrow(() -> new RuntimeException("PhotoBoard not found with seq: " + photoBoardSeq));

            // PhotoBoard 엔티티 삭제
            photoBoardDetailImgRepository.deleteByPhotoBoard(photoBoard);
            log.info("DetailImages deleted with SEQ: {}", photoBoardSeq);

            //관련 photoBoardDetailImg 삭제
            photoBoardRepository.delete(photoBoard);
            log.info("PhotoBoard deleted with SEQ: {}", photoBoardSeq);

            String message = "PhotoBoard deleted successfully";
            log.info(message);
            return message;
        }


        @Override
        public Resource getMainImg (String imgName){
            Resource resource = new FileSystemResource(uploadDir + "\\" + imgName);
            log.info("Main image loaded: {}", imgName);
            return resource;
        }

        @Override
        public Resource getDetailImg (String imgName){
            Resource resource = new FileSystemResource(uploadDir + "/detail" + "\\" + imgName);
            log.info("Detail image loaded: {}", imgName);
            return resource;
        }
    }
