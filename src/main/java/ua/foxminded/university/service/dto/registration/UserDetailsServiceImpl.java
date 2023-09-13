package ua.foxminded.university.service.dto.registration;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.foxminded.university.entity.User;
import ua.foxminded.university.repository.UserRepository;
import ua.foxminded.university.service.MyUserDetails;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUserByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user!");
        }
        return new MyUserDetails(user.get());
    }
}
