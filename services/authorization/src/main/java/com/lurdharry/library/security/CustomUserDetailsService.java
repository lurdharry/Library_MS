package com.lurdharry.library.security;

import com.lurdharry.library.exception.ResponseException;
import com.lurdharry.library.user.User;
import com.lurdharry.library.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
                ()-> new ResponseException("User not found!", HttpStatus.NOT_FOUND)
        );

        return  new CustomUserDetails(user);
    }
}
