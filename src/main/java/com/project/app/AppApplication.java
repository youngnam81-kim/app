package com.project.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.app.user.entity.User;
import com.project.app.user.repository.UserRepository;


@SpringBootApplication
//@SpringBootApplication(exclude = {BatchAutoConfiguration.class})
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner createTestUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // "testuser" 아이디를 가진 사용자가 없으면 생성
            if (!userRepository.existsByUserId("user")) {
                User testUser = new User();
                testUser.setUserId("user");
                testUser.setUserName("일반");
                testUser.setPassword(passwordEncoder.encode("user")); // "0000"을 암호화
                testUser.setAuth("USER"); // 예: 일반 사용자 권한
                userRepository.save(testUser);
                System.out.println("테스트 사용자 생성 완료: ID=testuser, PW=0000 (암호화됨)");
                System.out.println("생성된 테스트 계정의 암호화된 비밀번호: " + testUser.getPassword());
            }

            // 필요하다면 다른 테스트 사용자도 추가 가능
            if (!userRepository.existsByUserId("admin")) {
                User adminUser = new User();
                adminUser.setUserId("admin");
                adminUser.setUserName("관리자");
                adminUser.setPassword(passwordEncoder.encode("admin")); // "adminpass" 암호화
                adminUser.setAuth("ADMIN"); // 예: 관리자 권한
                userRepository.save(adminUser);
                System.out.println("관리자 테스트 사용자 생성 완료: ID=adminuser, PW=adminpass (암호화됨)");
            }
        };
    }

}
