package ua.foxminded.university.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.foxminded.university.entity.User;
import ua.foxminded.university.repository.UserAccountRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserAccountRepository userAccountRepository;

    @Autowired
    public UserDetailsServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userAccountRepository.getUserByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user!");
        }
        return new MyUserDetails(user);
    }
}
