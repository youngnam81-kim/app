package com.project.app.user.service; // 패키지 변경

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.app.user.dto.UserRequestDto;
import com.project.app.user.entity.User;
import com.project.app.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    
    @Transactional
    public User insertUser(UserRequestDto userRequestDto) {
        User user = User.builder()
                .userId(userRequestDto.getUserId())
                .userName(userRequestDto.getUserName())
                .password(passwordEncoder.encode(userRequestDto.getPassword())) // 비밀번호 암호화
                .auth("USER") // 기본 권한 설정
                .build();
        
        return userRepository.save(user);
    }
    
    // 아이디 존재 유무조회    
    public boolean existByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }
    
}