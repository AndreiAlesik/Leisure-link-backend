package work.service.user;

import org.springframework.web.multipart.MultipartFile;
import work.domain.User;
import work.dto.ResponseObject;
import work.dto.user.GetUserIdDTO;
import work.dto.user.userdetails.GetUserDetailsDTO;
import work.dto.user.userdetails.UpdateUserDetailsDTO;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;

public interface UserService {


    ResponseObject createUser(User user);

    ResponseObject confirmRegistration(String code);

    String refresh(String email);

    GetUserDetailsDTO getUserDetails(UUID userId);

    ResponseObject updateUserDetails(UpdateUserDetailsDTO updateUserDetailsDTO, UUID userId);

    ResponseObject updateUserImage(UUID userId, MultipartFile photo);

    GetUserIdDTO getMyId(HttpServletRequest request);
}
