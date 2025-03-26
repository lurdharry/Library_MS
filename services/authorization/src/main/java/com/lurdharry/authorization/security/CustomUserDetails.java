package com.lurdharry.authorization.security;

import com.lurdharry.authorization.user.Permission;
import com.lurdharry.authorization.user.RoleEntity;
import com.lurdharry.authorization.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class CustomUserDetails implements UserDetails {

    private final User user;
    private final List<GrantedAuthority> authorities = new ArrayList<>();

    public CustomUserDetails(User user){
        this.user = user;
        RoleEntity entity = user.getRole();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + entity.getName()));
        for(Permission permission : entity.getPermissions()){
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return user.isVerified();
    }

}
