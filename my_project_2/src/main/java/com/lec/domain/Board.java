package com.lec.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude="member")
@Entity
public class Board {

	@Id @GeneratedValue
	private Long seq;
	
	private String type;
	
	private String itemName;
	
	private Double degree;
	
	private Double flavorSweet; // 단맛
	private Double flavorBody; // 바디감
	private Double flavorPop; // 청량감
	private Double flavorSour; // 신맛
	private Double score; // 만족도
	private String comment; // 한 줄 평가
	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")
	private Long cnt;
/*	
	private String fileName;
	
	@Transient
	private MultipartFile uploadFile;	
*/	
	@Column(columnDefinition = "VARCHAR(10) default ''")
	private String tag1;
	@Column(columnDefinition = "VARCHAR(10) default ''")
	private String tag2;
	@Column(columnDefinition = "VARCHAR(10) default ''")
	private String tag3;
	
	@ManyToOne
	@JoinColumn(name="member_id", nullable = false, updatable = false)
	private Member member;
	
	public void setMember(Member member) {
		this.member = member;
		member.getBoardList().add(this);
	}
	
	@OneToOne
	@JoinColumn(name="flavor_id", nullable = false, updatable = false)
	private Detail detail;
	
}
