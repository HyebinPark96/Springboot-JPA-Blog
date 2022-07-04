package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴!!
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title; 
	
	@Lob // 대용량 데이터 쓸 때 사용 (MySQL : longtext형)
	private String content; // 섬머노트 라이브러리 <html> 태그가 섞여서 디자인 된다. => 크기 커진다.
	
	@ColumnDefault("0") // int 이므로  홑 따옴표 필요없음
	private int count; // 조회수 
	
	// mappedBy = "board" : board는 Reply테이블의 필드 이름 
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // mappedBy : 연관관계의 주인이 아니다 (난 FK가 아니에요) DB에 컬럼을 만들지 마세요. Board를 select할 때 JOIN문을 통해 값을 얻기 위해 필요한 것입니다.
	// @JoinColumn(name="replyId") // 생략가능한 이유 : FK 설정해버리면 1정규화(컬럼은 하나의 값을 가진다.)부정하므로 
	private List<Reply> reply; // 하나의 게시글에 여러 댓글 달리므로 List형
	
	// Many = Board, One = User : 한 명의 유저는 여러 개의 게시글을 쓸 수있다는 의미
	@ManyToOne(fetch = FetchType.EAGER) // EAGER : 값이 하나이므로 바로 가져온다는 의미
	@JoinColumn(name="userId") // @JoinColumn(name="") : 외래 키를 매핑 할 때 사용하며, name 속성에는 매핑 할 외래 키 이름을 지정
	// Mybatis는 관계에 있는 테이블의 PK를 멤버 변수로 갖지만, JPA는 관계에 있는 엔티티 객체를 참조하고 있다.
	private User user; // DB는 Object를 저장할 수 없다. FK, Java는 Object를 저장할 수 있다.  
	
	@CreationTimestamp
	private Timestamp createDate; 
	
}
