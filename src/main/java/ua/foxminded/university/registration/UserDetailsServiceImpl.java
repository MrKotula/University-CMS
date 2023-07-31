package ua.foxminded.university.registration;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.foxminded.university.entity.UserAccount;
import ua.foxminded.university.repository.UserAccountRepository;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> user = userAccountRepository.getUserByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user!");
        }
        return new MyUserDetails(user.get());
    }
}
