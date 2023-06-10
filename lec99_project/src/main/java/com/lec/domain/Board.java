package com.lec.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Board {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	
	private String title;
	
	private String content;
	
	@Column(insertable = false, updatable = false, columnDefinition = "date default now()")
	private Date createDate;
	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")
	private Long cnt;
	
	@ManyToOne
	@JoinColumn(name="MEMBER_ID", nullable = false, updatable = false)
	private Member member;
	
	public void setMember(Member member) {
		this.member = member;
		if (!member.getBoardList().contains(this)) {
	        member.getBoardList().add(this);
	    }
	}
	
}
