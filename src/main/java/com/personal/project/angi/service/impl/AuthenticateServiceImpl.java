package com.personal.project.angi.service.impl;

import com.personal.project.angi.enums.MessageResponseEnum;
import com.personal.project.angi.enums.ResponseCodeEnum;
import com.personal.project.angi.exception.response.ResponseBuilder;
import com.personal.project.angi.mapping.UserMapper;
import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.UserRegisterRequest;
import com.personal.project.angi.model.enity.UserInfoModel;
import com.personal.project.angi.repository.UserRepository;
import com.personal.project.angi.service.AuthenticateService;
import com.personal.project.angi.service.UserElkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserElkService userElkService;

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

            try{
                userRepository.save(userInfoModel);
                userElkService.saveOrUpdateUser(userInfoModel);
            }catch (Exception e){
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
