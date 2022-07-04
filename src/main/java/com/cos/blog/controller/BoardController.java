package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.service.BoardService;

// @AuthenticationPrincipal PrincipalDetail principal
@Controller
public class BoardController { // 컨트롤러에서 세션을 어떻게 찾는지?
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping({"", "/"})
	// /WEB-INF/views/index.jsp
	public String index(Model model, @PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("boards", boardService.글목록(pageable));
		return "index"; // viewResolver 작동!!
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) { // @PathVariable : url 변수의 값이 매개변수의 인자로 들어오는 기능 (같은 이름이어야 한다.)
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/detail";
	}
	
	// USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
} 
