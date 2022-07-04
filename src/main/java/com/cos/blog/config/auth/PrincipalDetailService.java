// 스프링 시큐리티에서 기본적으로 제공하는 Id : user, 비밀번호 : 콘솔창 해쉬값 의 계정이 아닌
// 내가 커스터마이징한 사용자계정을 사용하기 위해 이 파일을 생성하고,
// 내가 만든 사용자계정 정보가 담긴 PrincipalDetail의 user객체가 생성되도록 한다.
package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service // 빈(Bean) 객체로 등록
public class PrincipalDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	// 스프링이 로그인 요청을 가로챌 때 username, password 2개를 가로채는데
	// password 부분 처리는 알아서 하므로 
	// username이 DB에 있는 지만 확인해주면 됨
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다." + username);
				});
		return new PrincipalDetail(principal); // 시큐리티의 세션에 유저 정보가 저장됨 (Type : UserDetails)
	}
}
