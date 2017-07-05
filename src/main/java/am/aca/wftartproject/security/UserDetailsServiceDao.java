package am.aca.wftartproject.security;

import am.aca.wftartproject.model.User;
import am.aca.wftartproject.model.UserProfile;
import am.aca.wftartproject.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 04-Jul-17
 */
@Service
public class UserDetailsServiceDao implements UserDetailsService {

    private final Logger LOGGER = Logger.getLogger(UserDetailsServiceDao.class);

    @Autowired
    private UserService userService;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUser(email);
        if(user == null){
            LOGGER.info("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),
                getGrantedAuthorities(user));
    }



    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (UserProfile userProfile : user.getUserProfiles()) {
            LOGGER.info("UserProfile : " + userProfile);
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userProfile.getType()));
        }
        LOGGER.info("authorities : "+ authorities);

        return authorities;
    }
}
