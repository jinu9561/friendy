package web.mvc.service.generalBoard;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import web.mvc.dto.generalBoard.CommunityBoardDTO;
import web.mvc.entity.generalBoard.CommunityBoard;
import web.mvc.repository.generalBoard.CommunityBoardRepository;
import web.mvc.repository.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommunityBoardServiceImpl implements CommunityBoardService {

    private final CommunityBoardRepository communityBoardRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public CommunityBoardDTO createCommunityBoard(CommunityBoardDTO communityBoardDTO) {
        CommunityBoard communityBoard = modelMapper.map(communityBoardDTO, CommunityBoard.class);
        communityBoard.setUser(userRepository.findById(communityBoardDTO.getUserSeq())
                .orElseThrow(() -> new RuntimeException("에러발생. exception 추후 수정")));
        communityBoard = communityBoardRepository.save(communityBoard);
        return modelMapper.map(communityBoard, CommunityBoardDTO.class);
    }

    @Override
    public CommunityBoardDTO getCommunityBoardById(Long commBoardSeq) {
        CommunityBoard communityBoard = communityBoardRepository.findById(commBoardSeq)
                .orElseThrow(() -> new RuntimeException("에러발생. exception 추후 수정"));
        return modelMapper.map(communityBoard, CommunityBoardDTO.class);
    }

    @Override
    public List<CommunityBoardDTO> getAllCommunityBoards() {
        return communityBoardRepository.findAll().stream()
                .map(board -> modelMapper.map(board, CommunityBoardDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommunityBoardDTO updateCommunityBoard(Long commBoardSeq, CommunityBoardDTO communityBoardDTO) {
        CommunityBoard communityBoard = communityBoardRepository.findById(commBoardSeq)
                .orElseThrow(() -> new RuntimeException("에러발생. exception 추후 수정"));

        modelMapper.map(communityBoardDTO, communityBoard);
        communityBoard = communityBoardRepository.save(communityBoard);
        return modelMapper.map(communityBoard, CommunityBoardDTO.class);
    }

    @Override
    public void deleteCommunityBoard(Long commBoardSeq) {
        CommunityBoard communityBoard = communityBoardRepository.findById(commBoardSeq)
                .orElseThrow(() -> new RuntimeException("에러발생. exception 추후 수정"));
        communityBoardRepository.delete(communityBoard);
    }
}
