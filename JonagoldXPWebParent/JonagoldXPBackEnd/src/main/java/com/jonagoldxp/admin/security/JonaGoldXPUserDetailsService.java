package com.jonagoldxp.admin.security;

import com.jonagoldxp.admin.user.UserRepository;
import com.jonagoldxp.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class JonaGoldXPUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepo.getUserByEmail(email);
        if (user != null) {
            return new JonagoldXPUserDetails(user);
        }
        return null; // blad
    }
}

