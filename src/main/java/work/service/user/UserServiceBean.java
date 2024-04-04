package work.service.user;

import org.springframework.web.multipart.MultipartFile;
import work.domain.User;
import work.dto.ResponseObject;
import work.dto.user.GetUserIdDTO;
import work.dto.user.userdetails.GetUserDetailsDTO;
import work.dto.user.userdetails.UpdateUserDetailsDTO;
import work.repository.UserDetailsRepository;
import work.repository.UserRepository;
import work.service.email.EmailDetails;
import work.service.email.EmailService;
import work.service.util.UtilService;
import work.util.exception.CustomException;
import work.util.exception.UserNotFoundException;
import work.util.mapstruct.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;


@AllArgsConstructor
@Slf4j
@Service
public class UserServiceBean implements UserService {
    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final UtilService utilService;


    @Override
    public ResponseObject createUser(User user) {
        var userFromDb = userRepository.findByEmail(user.getEmail());

        if (userFromDb.isEmpty()) {
            user.setIsActivated(Boolean.FALSE);
//            user.setAppUserRoles(AppUserRole.ROLE_USER);
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCodeTimeGenerated(ZonedDateTime.now());
            var userDetails = user.getUserDetails();
            user.setUserDetails(null);
            var savedUser = userRepository.saveAndFlush(user);
            var savedUserDetails = userDetailsRepository.saveAndFlush(userDetails);
            userRepository.setUserDetailsId(savedUser.getId(), savedUserDetails.getId());
            userDetailsRepository.setUserDetailsId(savedUser.getId(), savedUserDetails.getId());
            emailService.emailConfirmation(new EmailDetails(user.getEmail(),
                    "Welcome to LeisureLink app " + savedUserDetails.getName()
                            + " "
                            + savedUserDetails.getLastName(), "Email confirmation"));
            return new ResponseObject(HttpStatus.ACCEPTED, "USER_CREATED", null);
        } else {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "USER_ALREADY_EXIST");
        }
    }

    @Override
    public ResponseObject confirmRegistration(String code) {
        var optionalTutor = userRepository.findByCode(code);
        if (optionalTutor.isPresent()) {
            long hours = getHours(optionalTutor);
            User user = optionalTutor.get();
            if (hours < 24) {
                user.setIsActivated(Boolean.TRUE);
                user.setCode(null);
                user.setCodeTimeGenerated(null);
                userRepository.save(user);
//                String token = jwtTokenProvider.createToken(
//                        user.getEmail(),
//                        new LinkedList<>(Collections.singletonList(user.getAppUserRoles())));
//                var userToken = new UserToken();
//                userToken.setToken(token);
//                userToken.setAppUserRole(user.getAppUserRoles());
                return new ResponseObject(HttpStatus.OK, "REGISTRATION_CONFIRMED", null);
            } else {

                return new ResponseObject(HttpStatus.OK, "VERIFICATION_LINK_WAS_SEND_AGAIN", null);
            }
        } else {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "INVALID_REGISTRATION_CODE", null);
        }
    }

    @Override
    public GetUserDetailsDTO getUserDetails(UUID userId) {
        var userDetails = userDetailsRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND"));
        var response = userMapper.getUserDetailsData(userDetails);
        if (userDetails.getPhoto() != null) {
            response.setPhoto(utilService.decompressImage(userDetails.getPhoto()));
        }
        return response;
    }

    @Override
    public ResponseObject updateUserDetails(UpdateUserDetailsDTO updateUserDetailsDTO, UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND"));
        var userDetails = userMapper.fromUpdateUserDetails(updateUserDetailsDTO);
        if (user.getUserDetails() != null)
            userDetails.setId(user.getUserDetails().getId());
        userDetails.setUser(user);
        userDetails = userDetailsRepository.saveAndFlush(userDetails);
        user.setUserDetails(userDetails);
        userRepository.save(user);
        return new ResponseObject(HttpStatus.ACCEPTED, "DATA_SUCCESSFULLY_UPDATED", null);
    }

    @Transactional
    public ResponseObject updateUserImage(UUID userId, MultipartFile photo) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND"));
        if (user.getUserDetails() != null) {
            user.getUserDetails().setPhoto(utilService.compressImage(photo, 0.5f));
            userRepository.save(user);
        }
        return new ResponseObject(HttpStatus.OK, "DATA_SUCCESSFULLY_UPDATED", null);
    }

    @Override
    public GetUserIdDTO getMyId(HttpServletRequest request) {
//        return new GetUserIdDTO(authenticationService.getUserByToken(request).getId());
        return null;
    }

    public String refresh(String email) {
        var tutor = userRepository.findByEmail(email);
        if (tutor.isEmpty()) {
            throw new UserNotFoundException("USER_WITH_THIS_EMAIL_NOT_FOUND");
        } else {
//            return jwtTokenProvider.createToken(email, Collections.singletonList(tutor.get().getAppUserRoles()));
            return null;
        }
    }


    private long getHours(Optional<User> optionalTutor) {
        Instant codeTimeGenerated = optionalTutor.get().getCodeTimeGenerated().toInstant();
        Instant now = Instant.now();

        Duration duration = Duration.between(codeTimeGenerated, now);
        long hours = duration.toHours();
        return hours;
    }
}
