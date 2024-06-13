package web.mvc.service.generalBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.dto.generalBoard.CommunityBoardDTO;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.entity.user.Users;
import web.mvc.repository.generalBoard.CommunityBoardRepository;
import web.mvc.repository.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityBoardServiceImpl implements CommunityBoardService {

    private final CommunityBoardRepository communityBoardRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CommunityBoardDTO> getAllCommunityBoards() {
        log.info("Fetching all community boards");
        List<CommunityBoard> communityBoards = communityBoardRepository.findAll();

        if (communityBoards == null || communityBoards.isEmpty()) {
            log.warn("게시물이 없습니다.");
            throw new RuntimeException("게시물이 없습니다.");
        }

        List<CommunityBoardDTO> communityBoardDTOs = communityBoards.stream()
                .map(CommunityBoardDTO::fromCommunityBoardEntity)
                .collect(Collectors.toList());

        log.info("Fetched {} community boards", communityBoardDTOs.size());
        return communityBoardDTOs;
    }

    @Transactional
    @Override
    public CommunityBoardDTO createCommunityBoard(CommunityBoardDTO communityBoardDTO) {
        log.info("Creating CommunityBoard with title: {}", communityBoardDTO.getBoardTitle());

        // 사용자 엔티티 조회
        Users user = userRepository.findById(communityBoardDTO.getUserSeq())
                .orElseThrow(() -> new RuntimeException("User not found with userid: " + communityBoardDTO.getUserSeq()));

        // 조회된 사용자 객체를 변환 메서드에 전달하여 CommunityBoard 엔티티 생성
        CommunityBoard communityBoard = communityBoardDTO.toCommunityBoardEntity(communityBoardDTO, user);

        // CommunityBoard 엔티티 저장
        CommunityBoard savedCommunityBoard = communityBoardRepository.save(communityBoard);
        log.info("CommunityBoard created with SEQ: {}", savedCommunityBoard.getCommBoardSeq());

        // 저장된 CommunityBoard 엔티티를 DTO로 변환하여 반환
        CommunityBoardDTO createdCommunityBoardDTO = CommunityBoardDTO.fromCommunityBoardEntity(savedCommunityBoard);
        log.info("CommunityBoardDTO created with SEQ: {}", createdCommunityBoardDTO.getCommBoardSeq());
        return createdCommunityBoardDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public CommunityBoardDTO getCommunityBoardById(Long commBoardSeq) {
        log.info("Fetching community board with SEQ: {}", commBoardSeq);

        // CommunityBoard 엔티티 조회
        CommunityBoard communityBoard = communityBoardRepository.findById(commBoardSeq)
                .orElseThrow(() -> new RuntimeException("CommunityBoard not found with seq: " + commBoardSeq));

        // 엔티티를 DTO로 변환하여 반환
        CommunityBoardDTO communityBoardDTO = CommunityBoardDTO.fromCommunityBoardEntity(communityBoard);
        log.info("Fetched community board with SEQ: {}", communityBoardDTO.getCommBoardSeq());
        return communityBoardDTO;
    }

    @Transactional
    @Override
    public CommunityBoardDTO updateCommunityBoard(CommunityBoardDTO communityBoardDTO) {
        long commBoardSeq = communityBoardDTO.getCommBoardSeq();
        log.info("Updating community board with SEQ: {}", commBoardSeq);

        // 기존 CommunityBoard 엔티티 조회
        CommunityBoard existingCommunityBoard = communityBoardRepository.findById(commBoardSeq)
                .orElseThrow(() -> new RuntimeException("CommunityBoard not found with seq: " + commBoardSeq));

        // 업데이트할 사용자 엔티티 조회
        Users user = userRepository.findById(communityBoardDTO.getUserSeq())
                .orElseThrow(() -> new RuntimeException("User not found with userid: " + communityBoardDTO.getUserSeq()));

        // 엔티티 필드 업데이트
        existingCommunityBoard.setBoardTitle(communityBoardDTO.getBoardTitle());
        existingCommunityBoard.setBoardContent(communityBoardDTO.getBoardContent());
        existingCommunityBoard.setBoardType(communityBoardDTO.getBoardType());
        existingCommunityBoard.setBoardPwd(communityBoardDTO.getBoardPwd());
        existingCommunityBoard.setBoardLike(communityBoardDTO.getBoardLike());
        existingCommunityBoard.setUser(user);

        // 엔티티 저장
        CommunityBoard updatedCommunityBoard = communityBoardRepository.save(existingCommunityBoard);
        log.info("CommunityBoard updated with SEQ: {}", updatedCommunityBoard.getCommBoardSeq());

        // 저장된 엔티티를 DTO로 변환하여 반환
        CommunityBoardDTO updatedCommunityBoardDTO = CommunityBoardDTO.fromCommunityBoardEntity(updatedCommunityBoard);
        log.info("CommunityBoardDTO updated with SEQ: {}", updatedCommunityBoardDTO.getCommBoardSeq());
        return updatedCommunityBoardDTO;
    }

    @Transactional
    @Override
    public String deleteCommunityBoard(Long commBoardSeq) {
        log.info("Deleting community board with SEQ: {}", commBoardSeq);

        // 삭제하려는 CommunityBoard 엔티티 조회
        CommunityBoard communityBoard = communityBoardRepository.findById(commBoardSeq)
                .orElseThrow(() -> new RuntimeException("CommunityBoard not found with seq: " + commBoardSeq));

        // CommunityBoard 엔티티 삭제
        communityBoardRepository.delete(communityBoard);
        log.info("CommunityBoard deleted with SEQ: {}", commBoardSeq);

        String message = "CommunityBoard deleted successfully";
        log.info(message);
        return message;
    }
}
