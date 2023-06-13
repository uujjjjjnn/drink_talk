package com.lec.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude="member")
@Entity
public class Detail {
	
	@Id @GeneratedValue
	private Long d_seq;
	
	private String type;
	
	private String itemName;
	
	private Double degree;

	private Double flavorSweet; // 단맛
	private Double flavorBody; // 바디감
	private Double flavorPop; // 청량감
	private Double flavorSour; // 신맛
	private Double score; // 만족도
	private String comment; // 한 줄 평가	
	
	@ManyToOne
	@JoinColumn(name="member_id", nullable = false, updatable = false)
	private Member member;
	
	public void setMember(Member member) {
		this.member = member;
		member.getDetailList().add(this);
	}
	
	@OneToOne
	@JoinColumn(name="b_seq", nullable = false, updatable = false)
	private Board board;
	
}
