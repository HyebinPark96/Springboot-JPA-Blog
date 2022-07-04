package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Getter;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료되면
// UserDetails 타입의 오브젝트를 스프링 시큐리티의 고유한 세션 저장소에 저장해줌
@Getter // 게시판 글쓰기에 필요한 User 컴포지션을 꺼내기 위한 어노테이션 
public class PrincipalDetail implements UserDetails{ // UserDetails 인터페이스의 추상메소드 오버라이딩 필요
	
	private User user; // composition : extends 단점을 보완하기 위해 composition 사용
	
	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	/* Alt+Shift+S => 오버라이딩 */
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 계정이 만료되지 않았는지 리턴한다. (true : 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겼는지 안잠겼는지 리턴한다. (true : 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호가 만료되지 않았는지 리턴한다 (true : 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정이 활성화 (사용가능)인지 리턴한다. (true : 활성화)
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	// 계정이 갖고 있는 권한 목록을 리턴한다.(권한이 여러 개 있을 수 있어서 루프를 돌아야 하는데 우리는 한 개만)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		Collection<GrantedAuthority> collectors = new ArrayList<>(); // ArrayList < List < Collection
//		collectors.add(new GrantedAuthority() { // GrantedAuthority 인터페이스 => 익명클래스 만들어주고, 
//			
//			// 추상메소드 오버라이딩
//			@Override
//			public String getAuthority() {
//				return "ROLE_" + user.getRole(); // 규칙 : ROLE_USER 
//			}
//		});
		
		// 위 코드를 람다식으로 변환 
		collectors.add(()->{return "ROLE_" + user.getRole();}); 
		
		return collectors;
	}
	
	
}
