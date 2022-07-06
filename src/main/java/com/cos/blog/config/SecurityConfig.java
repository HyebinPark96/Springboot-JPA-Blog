package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

// 빈(Bean) 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration // 빈 등록하는 어노테이션 (IoC)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다. => 스프링 시큐리티가 활성화 되어 있는데, 어떤 설정을 해당 파일에서 하겠다는 의미
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증을 미리 체크하겠다는 의미 
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean
	@Override // 오버라이딩 :  ALT + SHIFT + S
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	@Bean // IoC가 됩니다.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder(); // 스프링이 관리
	}
	

	// 시큐리티가 대신 로그인해줄 때 password를 가로채기하는데, 
	// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는 지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	
	// 시큐리티
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)
			.authorizeRequests()
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**")
				.permitAll()
				.anyRequest() // 이외 요청들은
				.authenticated() // 인증되어야 함
			.and()
				.formLogin()
				.loginPage("/auth/loginForm") // 인증 필요한 곳에서 로그인 페이지가 뜰 것
				.loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청하는 로그인을 가로채서 대신 로그인 해준다.
				// 로그인 성공하면 세션에 UserDetails 타입으로 저장해야하는데, User 오브젝트가 날아오므로 변환과정 필요함
				.defaultSuccessUrl("/");  // 로그인 성공하면 index로 
	}
	
}
