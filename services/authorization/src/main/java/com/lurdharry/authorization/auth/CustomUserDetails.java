package com.lurdharry.authorization.auth;

import com.lurdharry.authorization.user.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(()->"ROLE_" + role);
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

}
