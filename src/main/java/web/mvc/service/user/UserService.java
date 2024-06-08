package web.mvc.service.user;


import web.mvc.dto.user.UsersDTO;



public interface UserService {

    // 가입 하기
    public String registerUser(UsersDTO usersDTO) ;

    // id 중복 체크
    public boolean duplicateIdCheck(String userId);

}
