package com.personal.project.angi.service.impl;

import com.personal.project.angi.enums.MessageResponseEnum;
import com.personal.project.angi.enums.ResponseCodeEnum;
import com.personal.project.angi.exception.response.ResponseBuilder;
import com.personal.project.angi.mapping.UserMapper;
import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.UserLoginRequest;
import com.personal.project.angi.model.dto.request.UserRegisterRequest;
import com.personal.project.angi.model.dto.response.JwtResponse;
import com.personal.project.angi.model.enity.UserInfoModel;
import com.personal.project.angi.repository.UserRepository;
import com.personal.project.angi.service.AuthenticateService;
import com.personal.project.angi.service.JwtService;
import com.personal.project.angi.service.UserElkService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {
    private final AuthenticationManager authenticationManager;
    private final HttpServletRequest request;

    private final UserMapper userMapper;

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserElkService userElkService;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public ResponseEntity<ResponseDto<Void>> register(UserRegisterRequest request) {
        try {
            UserInfoModel userInfoModel = userMapper.toUserInfoModel(request);

            if (isUserExist(userInfoModel.getUsername())) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.CREATED_USER_FAILED.getMessage(),
                        ResponseCodeEnum.USER0200);
            }

            encryptPassword(userInfoModel);

            try {
                userRepository.save(userInfoModel);
                userElkService.saveOrUpdateUser(userInfoModel);
            } catch (Exception e) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.CREATED_USER_FAILED.getMessage(),
                        ResponseCodeEnum.USER0201);
            }

            return ResponseBuilder.okResponse(
                    MessageResponseEnum.CREATED_USER_SUCCESS.getMessage(),
                    ResponseCodeEnum.USER1200);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.CREATED_USER_FAILED.getMessage(),
                    ResponseCodeEnum.USER0202);
        }

    }

    @Override
    public ResponseEntity<ResponseDto<JwtResponse>> login(UserLoginRequest request) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.USERNAME_PASSWORD_NOT_CORRECT.getMessage(),
                    ResponseCodeEnum.LOGIN0200);
        }
        try {
            JwtResponse token = jwtService.generateToken(authentication);
            return ResponseBuilder.okResponse(
                    MessageResponseEnum.LOGIN_SUCCESS.getMessage(),
                    token,
                    ResponseCodeEnum.LOGIN1200);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.LOGIN_FAILED.getMessage(),
                    ResponseCodeEnum.LOGIN0201);
        }
    }

    @Override
    public ResponseEntity<ResponseDto<Void>> logout() {
        try {
            String token = jwtService.getTokenFromRequest(request);
            if (token == null) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.LOGOUT_FAILED.getMessage(),
                        ResponseCodeEnum.LOGOUT0200);
            }
            jwtService.revokeAccessToken(token);
            return ResponseBuilder.okResponse(
                    MessageResponseEnum.LOGOUT_SUCCESS.getMessage(),
                    ResponseCodeEnum.LOGOUT1200);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.LOGOUT_FAILED.getMessage(),
                    ResponseCodeEnum.LOGOUT0200);
        }
    }

    @Override
    public ResponseEntity<ResponseDto<JwtResponse>> refreshToken(String refreshToken) {
        try {
            if (!jwtService.validateRefreshToken(refreshToken)) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.REFRESH_TOKEN_FAILED.getMessage(),
                        ResponseCodeEnum.REFRESHTOKEN0201);
            }
            String userId = jwtService.getUserIdFromRefreshToken(refreshToken);

            UserDetails userDetails = userDetailsService.loadUserById(userId);
            if (userDetails == null) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.REFRESH_TOKEN_FAILED.getMessage(),
                        ResponseCodeEnum.REFRESHTOKEN0202);
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            JwtResponse newToken = null;
            try {
                newToken = jwtService.generateToken(authentication);
            } catch (Exception e) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.REFRESH_TOKEN_FAILED.getMessage(),
                        ResponseCodeEnum.REFRESHTOKEN0203);
            }
            jwtService.revokeRefreshToken(refreshToken);

            return ResponseBuilder.okResponse(
                    MessageResponseEnum.REFRESH_TOKEN_SUCCESS.getMessage(),
                    newToken,
                    ResponseCodeEnum.REFRESHTOKEN1200
            );

        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.REFRESH_TOKEN_SUCCESS.getMessage(),
                    ResponseCodeEnum.REFRESHTOKEN0200);
        }
    }

    private Boolean isUserExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private void encryptPassword(UserInfoModel userInfoModel) {
        try {
            String password = userInfoModel.getPassword();
            String encodedPassword = bCryptPasswordEncoder.encode(password);
            userInfoModel.setPassword(encodedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting password");
        }
    }
}
