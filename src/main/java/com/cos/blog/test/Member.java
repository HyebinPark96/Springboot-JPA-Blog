package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// @Getter
// @Setter
@Data // @Getter, @Setter 동시에 만들어줌
@NoArgsConstructor
// @RequiredArgsConstructor // final 붙은 객체에 Constructor 생성해줌
public class Member {
	// private로 만드는 이유 : 바로 객체에 접근하면 객체지향 의미가 없으므로
	private int id;
	private String username; 
	private String password; 
	private String email;
	
	@Builder // 장점 : 1. 객체에 값을 넣을때 순서를 지키지 않아도 된다. 2. 객체 값 순서를 헷갈려서 객체의 값을 잘못 넣는 실수하는 것을 방지한다.
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
}
