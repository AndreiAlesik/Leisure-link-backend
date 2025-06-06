package work.util.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import work.domain.User;
import work.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final Optional<User> appUser = userRepository.findByEmail(username);

    if (appUser.isEmpty()) {
      throw new UsernameNotFoundException("User '" + username + "' not found");
    }
    var user = appUser.get();
    return org.springframework.security.core.userdetails.User //
        .withUsername(username) //
        .password(user.getPassword()) //
        .authorities(user.getAppUserRoles()) //
        .accountExpired(false) //
        .accountLocked(false) //
        .credentialsExpired(false) //
        .disabled(false) //
        .build();
  }
}
