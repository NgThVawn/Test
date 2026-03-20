package laptrinhJ2EE.nguyenthanhvan.services;

import laptrinhJ2EE.nguyenthanhvan.entity.CustomUserDetail;
import laptrinhJ2EE.nguyenthanhvan.entity.User;
import laptrinhJ2EE.nguyenthanhvan.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new CustomUserDetail(user, userRepository);
    }
}
