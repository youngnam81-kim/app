package com.project.app.user.service; // 패키지 변경

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.app.user.dto.LoginRequestDto;
import com.project.app.user.dto.LoginResponseDto;
import com.project.app.user.entity.User;
import com.project.app.user.repository.UserRepository;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;
    
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        return userRepository.findByUserId(loginRequestDto.getUserId())
                .filter(user -> user.getPassword().equals(loginRequestDto.getPassword()))
                .map(user -> LoginResponseDto.builder()
                        .id(user.getId())
                        .userId(user.getUserId())
                        .userName(user.getUserName())
                        .auth(user.getAuth())
                        .success(true)
                        .message("로그인 성공")
                        .build())
                .orElse(LoginResponseDto.builder()
                        .success(false)
                        .message("아이디 또는 비밀번호가 일치하지 않습니다.")
                        .build());
    }

    // 아이디로 사용자 조회
    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
}