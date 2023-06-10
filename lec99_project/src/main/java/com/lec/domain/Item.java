package com.lec.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Item {

	@Id
	private String id; // 상품명
	private String type; // 주종
	private String degree; // 도수
	private Double flavorSweet; // 단맛
	private Double flavorBody; // 바디감
	private Double flavorPop; // 청량감
	private Double flavorSour; // 신맛
	private Double score; // 만족도
	private String comment; // 한 줄 평가
	
	@ManyToOne
	@JoinColumn(name="MEMBER_ID", nullable = false, updatable = false)
	private Member member;
	
	public void setMember(Member member) {
		this.member = member;
		if(!member.getItemList().contains(this)) {
			member.getItemList().add(this);
		}
	}
}
