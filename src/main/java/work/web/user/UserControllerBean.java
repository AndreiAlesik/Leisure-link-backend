package work.web.user;


import org.springframework.web.multipart.MultipartFile;
import work.dto.ResponseObject;
import work.dto.user.*;
import work.dto.user.userdetails.GetUserDetailsDTO;
import work.dto.user.userdetails.UpdateUserDetailsDTO;
import work.service.user.UserService;
import work.util.mapstruct.UserMapper;
//import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.security.Principal;


@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Tag(name = "User", description = "User API")
@CrossOrigin
public class UserControllerBean implements UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    public ResponseObject userRegisterAccount(@Valid RequestUserDTO requestUserDto) {
        var newUser = userMapper.requestUserDtoToUser(requestUserDto);
        return userService.createUser(newUser);
    }

    @Override
    public ResponseObject login(RequestLoginDTO userLoginDto) {
//        return userService.signin(userMapper.fromRequestUserDto(userLoginDto));
        return null;
    }

    @Override
    public GetUserDetailsDTO getUserDetails(HttpServletRequest request, Principal principal) {
//        return userService.getUserDetails(authenticationService.getUserByToken(request).getId());
        return null;
    }

    @Override
    public ResponseObject updateUserDetails(HttpServletRequest request, UpdateUserDetailsDTO detailsDTO) {
//        return userService.updateUserDetails(detailsDTO, authenticationService.getUserByToken(request).getId());
        return null;
    }

    @Override
    public ResponseObject updateUserImage(HttpServletRequest request, MultipartFile photo) {
//        return userService.updateUserImage(authenticationService.getUserByToken(request).getId(), photo);
    return null;
    }

    @Override
    public GetUserIdDTO getMyId(HttpServletRequest request, Principal principal) {
        return userService.getMyId(request, principal);
    }
}
