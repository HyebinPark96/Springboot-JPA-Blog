package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 서비스 1. 트랜잭션 관리 2. 서비스 의미 때문
@Service // 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해 줌 (=IOC 해줌)
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional // import springframework
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); // 1234 원본
		String encPassword = encoder.encode(rawPassword); // 해쉬
		user.setPassword(encPassword); // 시큐리티 적용
		user.setRole(RoleType.USER); // 회원가입 시 입력받지 않으므로 따로 셋팅해줘야 함
		userRepository.save(user);
	}
	
	/*로그인 시큐리티 적용할 것이므로 사용X*/
//	@Transactional(readOnly = true) // SELECT할 때 트랜잭션 시작, 서비스 종료시 트랜잭션 종료 (정합성 유지)
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
}
