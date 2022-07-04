package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

// DAO와 유사한 기능을 하는 인터페이스
// 자동으로 bean 등록이 된다.
// @Repository 생략 가능
/* 
이유 : Repository를 직접 구현할때는 @Repository의 어노테이션이 필요했지만,
JpaRepository인터페이스를 상속 받아 사용할 때는 생략이 가능하다.
이유는, 컴포넌트 스캔을 스프링 데이터JPA가 자동으로 처리하기 때문.
또한, JPA 예외를 스프링 예외로 변환하는 과정도 자동으로 처리
*/
public interface UserRepository extends JpaRepository<User, Integer>{
	// SELECT * FROM user WHERE username = 1?;
	Optional<User> findByUsername(String username);
	
}







/*로그인 시큐리티 사용할 것이므로 사용X*/

// 1.  JPA Naming 쿼리
// SELECT * FROM user WHERE username = ?1 AND password = ?2 : ?에 매개변수가 차례로 들어감
// User findByUsernameAndPassword(String username, String password);

// 2. 네이티브 쿼리로 위 코드와 같은 기능
//@Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//User login(String username, String password);