package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;

// 서비스 1. 트랜잭션 관리 2. 서비스 의미 때문
@Service // 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해 줌 (=IOC 해줌)
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional // import springframework
	public void 글쓰기(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

}
