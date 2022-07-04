package com.cos.blog.test;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController 
public class DummyControllerTest {
	
	@Autowired 
	private UserRepository userRepository;
	
	@DeleteMapping("dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) { // Exception가 부모 클래스이므로 Exception e 해도 가능
			return "삭제에 실패하였습니다. 해당 ID는 DB에 없습니다.";
		}
		
		return "삭제되었습니다. id : " + id;
	}
	
	
	// save() 메소드는 id를 전달하지 않으면 INSERT
	// save() 메소드는 id를 전달하면 해당 id에 대한 데이터가 있으면 UPDATE
	// save() 메소드는 id를 전달하면 해당 id에 대한 데이터가 없으면 INSERT
	@Transactional // 함수 종료시에 자동 commit 이 됨
	@PutMapping("/dummy/user/{id}") // UPDATE이므로 @PutMapping 어노테이션 사용
	public User updateUser(@PathVariable int id, @RequestBody /*JSON 데이터*/ User requestUser) { // JSON 데이터를 요청 => Java Object(MessageConverter)의 Jackson 라이브러리가 변환해서 받아준다.
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new  IllegalArgumentException("수정에 실패하였습니다.");
		});
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		// userRepository.save(user); // 보통은 INSERT 할 때 save 사용하지만 UPDATE도 사용가능
		
		// 더티체킹 : 영속성 컨텍스트의 1차캐시에 담긴 Object와 영속화된 Object 가져와서 수정한 Object를 비교하여 변경감지 되었을 때 DB로 UPDATE문을 날려 수정
		return user;		
	}
	

	// http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	
	// 한 페이지당 2건의 데이터(size=2)를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);
		
//		if(pagingUser.isLast()) { // 분기가능
//			
//		}
		List<User> users = pagingUser.getContent();
		return pagingUser;
	}

	
	// {id} 주소로 파라미터를 전달 받을 수 있음
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id){ // 파라미터의 변수명은 맵핑의 {}안 파라미터명과 동일하게 지정해야 맵핑됨
		// 1. 람다식 사용
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 사용자가 존재하지 않습니다.");
		});
		
		
		// 2. Supplier 인터페이스 사용
//		// Optional 리턴하는 이유 : 없는 데이터를 findById로 찾을 때 null 리턴될 수 있으므로, Optional로 User 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return하기 위해
//		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() { // Supplier : 인터페이스
//			@Override
//			public IllegalArgumentException get() {
//				return new IllegalArgumentException("해당 유저는 존재하지 않습니다. id : " + id); // 존재하지 않을 경우
//			} 
//		});
		
		// 요청 : 웹브라우저
		// user 객체 = 자바 Object
		// 웹브라우저가 이해할 수 있는 데이터(JSON (GSon 라이브러리 활용하여 변환))로 변환해서 전달해야 한다.
		// 스프링부트는 MessageConveter가 응답시에 자동으로 작동
		// 만약 자바 Object를 리턴하게 되면 MessageConveter가 Jackson 라이브러리를 호출해서 
		// user Object를 JSon으로 변환해서 브라우저에게 던져준다.
		return user; // 존재할 경우
	}
	
	
	
	/* 1. 변수과 필드명 맞춰서 RequestParam 생략하여 받기 */
	// http://localhost:8000/blog/dummy/join(요청)
	// http의 body에 username, password, email  데이터를 가지고(요청)
//	@PostMapping("/dummy/join")
//	public String join(/* 변수명과 필드명 맞춰주면 생략 가능 @RequestParam("username") */String username, String password, String email) {// x-www-form-urlencoded : key=value&key=value...(약속된 규칙)
//		System.out.println("username : " + username + ", password : " + password + ", email : " + email);
//		return "회원가입이 완료되었습니다.";
//	}
	
	/* 2. Object로 받기 */
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		
		// user.setRole("user"); String -> Enum 으로 변경
		user.setRole(RoleType.USER); // RoleType만 가능하므로 String 잘못 입력하는 실수 예방할 수 있음
		
		userRepository.save(user); // 저장
		
		return "회원가입이 완료되었습니다.";
	}
	
	
	
	
}
