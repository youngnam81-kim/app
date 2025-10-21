package com.project.app.user.controller; // 패키지 변경

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.app.config.security.JwtTokenProvider;
import com.project.app.user.dto.LoginRequestDto;
import com.project.app.user.dto.LoginResponseDto;
import com.project.app.user.entity.User;
import com.project.app.user.service.LoginService;

@RestController
//@RequestMapping("/api")
@RequestMapping(value = {"/api", "/app"})
public class LoginController {

	private final LoginService loginService;
	
	private final JwtTokenProvider jwtTokenProvider;
	private PasswordEncoder passwordEncoder;

	public LoginController(LoginService loginService, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
		this.loginService = loginService;
		this.jwtTokenProvider = jwtTokenProvider;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
		// 사용자 찾기
		User user = loginService.findByUserId(loginRequestDto.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디입니다."));
		// 비밀번호 검증
		if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		}
		
		// 권한 정보 설정
		List<String> roles = new ArrayList<>();
		roles.add(user.getAuth());

		// JWT 토큰 생성
		String token = jwtTokenProvider.createToken(user.getUserId(), roles);

		// 응답 데이터 구성 (토큰과 사용자 정보 모두 반환)
		Map<String, Object> response = new HashMap<>();
		response.put("token", token);
		response.put("user", LoginResponseDto.builder().id(user.getId()).userId(user.getUserId())
				.userName(user.getUserName()).auth(user.getAuth()).success(true).message("로그인 성공").build());

		return ResponseEntity.ok(response);
	}
}