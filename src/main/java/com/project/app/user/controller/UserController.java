package com.project.app.user.controller; // 패키지 변경

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.app.config.security.JwtTokenProvider;
import com.project.app.user.dto.UserRequestDto;
import com.project.app.user.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<?> insertUser(@RequestBody UserRequestDto userRequestDto) {
		try {
			if (userRequestDto.getUserId() == null || userRequestDto.getPassword() == null) {
				return ResponseEntity.badRequest().body("아이디와 비밀번호는 필수 항목 입니다.");
			}
			if (userService.existByUserId(userRequestDto.getUserId())) {
				return ResponseEntity.badRequest().body("이미 사용중인 아이디입니다.");
			}

			userService.insertUser(userRequestDto);

			return ResponseEntity.status(HttpStatus.CREATED).body(userRequestDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("회원가입 처리 중 오류가 발생했습니다. : " + e.getMessage());
		}
	}

}