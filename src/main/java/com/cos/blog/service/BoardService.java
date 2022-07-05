package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
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
	
	@Transactional(readOnly = true) // SELECT 이므로 readOnly = true 설정
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable); // 페이징되어 호출
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id) // findById : Optinal 형식이므로 null값일 경우 예외처리 해줘야 하므로 .orElseThrow() 사용
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});	
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id); //  deleteById: JPA 제공
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		// 수정하기 위해 영속화 해줘야 함
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				});	 // 영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수 종료시(=Service가 종료될 때) 트랜잭션이 종료. 이 때 더티체킹이 일어나며 자동 업데이트가 됨 (=DB flush)
	}
}
