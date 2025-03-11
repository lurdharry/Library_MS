package com.lurdharry.authorization.auth;

import com.lurdharry.authorization.user.User;
import com.lurdharry.authorization.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         User user = repository.findByEmail(email)
                 .orElseThrow(() -> new UsernameNotFoundException("User not found"));

         return org.springframework.security.core.userdetails.User
                 .withUsername(user.getEmail())
                 .password(user.getPassword())
                 .authorities(List.of( new SimpleGrantedAuthority("ROLE_"+user.getRole())))
                 .build();
    }
}
