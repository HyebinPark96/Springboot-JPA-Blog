package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController // 앱에도 사용가능하도록 data리턴하는 @RestController 어노테이션 사용
public class UserApiController {
	
	@Autowired
	private UserService userService; // 스프링이 컴포넌트 스캔을 통해서 UserService.java의 @Service 어노테이션을 보면 Bean에 등록을 해 줌 (=IOC 해줌)
	
//	@Autowired
//	private HttpSession session; // 세션은  매개변수로 받거나 자동주입 둘 다 가능
	
	
	// 회원가입완료 버튼 클릭 시 발생
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { // JSON 데이터 요청받으므로 @RequestBody 어노테이션 사용
		System.out.println("UserApiController : save 호출됨");
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // HttpStatus.OK : 200 (통신 정상 의미) 리턴, enum이므로 숫자 200 입력하는 것보다 오타 줄여서 안전, result 1 리턴하면 성공 -1 리턴하면 실패
	}
	
//	// 스프링 시큐리티 이용해서 로그인할 예정이므로 아래 방식 사용 안할 것
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user/*, HttpSession session*/){ 
//		System.out.println("UserApiController : login 호출됨");
//		User principal = userService.로그인(user); // principal : 접근주체
//		
//		if(principal != null) {
//			session.setAttribute("principal", principal); // 세션 생성
//		}
//		
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
//	}
	
	
	
}
