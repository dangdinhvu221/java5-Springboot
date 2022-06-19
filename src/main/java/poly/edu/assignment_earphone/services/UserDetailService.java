package poly.edu.assignment_earphone.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import poly.edu.assignment_earphone.models.Users;
import poly.edu.assignment_earphone.repositories.IUserRepository;
import poly.edu.assignment_earphone.utility.EncrytedPasswordUtils;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private IUserRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Users users = usersRepository.findByUsernameLike(username);
        if (users == null) {
            throw new UsernameNotFoundException(username);
        }
        return User.withUsername(users.getUsername()).password(EncrytedPasswordUtils.encrytePassword(users.getPassword())).authorities(users.getTypeRole().toString()).build();
    }
}