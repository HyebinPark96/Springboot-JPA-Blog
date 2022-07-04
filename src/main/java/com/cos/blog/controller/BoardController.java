package com.cos.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController { // 컨트롤러에서 세션을 어떻게 찾는지?
	@GetMapping({"", "/"})
	// /WEB-INF/views/index.jsp
	public String index(@AuthenticationPrincipal PrincipalDetail principal) {
		System.out.println("로그인 사용자 아이디 : " + principal.getUsername()); // 로그인 성공했는지 테스트용
		return "index";
	}
}
