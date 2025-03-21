package com.lurdharry.authorization.auth;

import com.lurdharry.authorization.model.User;
import com.lurdharry.authorization.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found!")
        );

        return  new CustomUserDetails(user.getEmail(),user.getPassword(),user.getRole());
    }
}
