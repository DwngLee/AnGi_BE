package com.personal.project.angi.service.impl;

import com.personal.project.angi.configuration.security.UserDetailsImpl;
import com.personal.project.angi.model.enity.UserInfoModel;
import com.personal.project.angi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails   loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfoModel> userInfoModel = userRepository.findByUsername(username);
        return userInfoModel.map(UserDetailsImpl::new).orElse(null);
    }

    public UserDetails loadUserById(String id) {
        Optional<UserInfoModel> userInfoModel = userRepository.findById(id);
        return userInfoModel.map(UserDetailsImpl::new).orElse(null);
    }
}
