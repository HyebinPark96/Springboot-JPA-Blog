package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	@Transactional  // 트랜잭션 : 서비스단에서 시작되고 종료 (종료되는 시점에 영속성컨텍스트에 1차캐시로 존재하면 flush 후) => commit 됨
	public void 회원수정(User user) {
		// 수정시에는 JPA 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// SELECT해서 User오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해!!
		// 영속화 하는 이유는 영속화된 오브젝트를 변경하면 자동으로 DB에 UPDATE문을 날려주므로
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		}); // 영속화 완료
		
		String rawPassword = user.getPassword(); // 기존 패스워드 가져오기
		String encPassword = encoder.encode(rawPassword); // 암호화
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail()); // 영속화된 오브젝트에서 이메일 수정
		
		// 회원 수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료  = 커밋 자동으로 됨
		// = 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어  변화된 것들에 대해 UPDATE문을 날려준다.

	}
	
}
