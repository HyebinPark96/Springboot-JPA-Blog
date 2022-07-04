package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴!!
//ORM : Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Entity // User클래스가 MySQL에 테이블로 생성된다. 
// @DynamicInsert // INSERT할 때 null인 필드 제외 => role 필드가 INSERT문에 추가되지 않아 null이 아닌 Default로 "user"로 INSERT 된다. 복잡해서 생략

public class User {
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType : 프로젝트에 연결된 DB의 넘버링 전략을 따라간다. (IDENTITY : 오라클-시퀀스, MySQL-AUTO_INCREMENT)
	private int id; // 비워도 자동으로 INSERT
	
	@Column(nullable = false, length = 30, unique = true)
	private String username; // 아이디
	
	@Column(nullable = false, length = 100) // ex) 123456 => 해쉬(비밀번호 암호화) 
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email; 
	
	// @ColumnDefault("'user'") // 복잡해서 생략
	@Enumerated(EnumType.STRING) // DB는 RoleType 이라는 게 없으므로 타입 지정해야 함
	private RoleType role; // String의 경우 오타 가능성 있으므로 Enum을 쓰는 게  좋다.
	
	@CreationTimestamp // 시간이 자동 입력 => 비워도 자동으로 INSERT
	private Timestamp createDate; 
}
