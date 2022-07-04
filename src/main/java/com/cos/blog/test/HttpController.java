package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

// 인터넷 브라우저 요청은 무조건 get요청밖에 할 수 없다.
// 사용자가 요청 -> 응답(HTML 파일)
// @Controller

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpController {
	
	private static final String TAG = "HttpControllerTest : ";
	
	// application.yml 설정 변경하였으므로 localhost:8000.blog/http/lombok 로 변경
	@GetMapping(value = "/http/lombok")
	public String lombokTest() {
		// @AllArgsConstructor로 만들어진 생성자 호출
		// Member m = new Member(1, "ssar", "1234", "email");
		
		// (권장) @Builder 사용할 때 생성자 호출
		Member m = Member.builder().username("SSAR").password("1234").email("ssar@nate.com").build();
		
		System.out.println(TAG + "getter : " + m.getId()); // 0
		m.setId(5000);
		System.out.println(TAG + "setter :  " + m.getId()); // 5000
		return "lombok 테스트 완료";
	}
	
	
	/* 
	 http://localhost:8080/http/get (SELECT)
	@GetMapping(value = "/http/get")
	@RequestParam : 하나씩 가져옴
	public String getTest(@RequestParam int id, @RequestParam String username) {
		return "get 요청 : " + id + ", " + username;
	} 
	*/
	
	@GetMapping(value = "/http/get")
	// id=1&username=ssar&password=1234&email=ssar@nate.com를 스프링이 Member 객체인 m에 넣어준다.
	public String getTest(Member m) { 
		return "get 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	// http://localhost:8080/http/post (INSERT)
	@PostMapping("/http/post")
	// @RequestBody : 
	public String postTest(@RequestBody Member m) { // MessageConverter (스프링부트)가 맵핑해줌
		return "post 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	// http://localhost:8080/http/put (UPDATE)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	// http://localhost:8080/http/delete (DELETE)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
}
