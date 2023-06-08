package com.lec.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Comment {

	@Id @GeneratedValue
	private Long commentNo;
	
	private String commentContent;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false, columnDefinition = "date default now()")
	private Date writeDate;

	/*
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SEQ", nullable = true)
	private Board board;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="NICK", nullable = true)
	private Member member;
	*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_seq", referencedColumnName = "seq")
	private Board board;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", referencedColumnName = "id")
	private Member member;
	
}
