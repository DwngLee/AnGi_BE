package com.personal.project.angi.configuration.security;

import com.personal.project.angi.model.enity.UserInfoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    UserInfoModel user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(user.getRoles()!=null){
            return user.getRoles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getUserId() {
        return user.getId();
    }
}
