package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Data가 아닌 File 리턴할 것이므로 
public class TempController {
	
	// http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		// 파일리턴시 기본경로 : src/main/resources/static
		// 그러므로 리턴명 :  /home.html 되어야 정상적인 경로가 되어 파일 찾아 리턴해줌
		// 풀경로 : src/main/resources/static/home.html
		return "/home.html";
	}
	
	@GetMapping("/temp/aaa")
	public String tempImg() {
		return "/aaa.png";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		// prefix : /WEB-INF/views/
		// suffix : .jsp
		// return "/test.jsp" 일 경우, 풀경로 : /WEB-INF/views//test.jsp.jsp
		// 그러므로 return "test" 일 경우, 풀경로 : /WEB-INF/views/test.jsp
		// return "/test.jsp";
		return "test";
	}
	
	
}
