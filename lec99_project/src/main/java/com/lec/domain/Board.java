package com.lec.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude= "commentList")
@Entity
public class Board {

	@Id @GeneratedValue
	private Long seq;
	
	private String title;
	
	// @Column(updatable = false)
	// private String writer;
	
	private String content;
	
	private String contentType;
	
	// @Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false, columnDefinition = "timestamp default current_timestamp()")
	private LocalDateTime createDate;
	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")
	private Long cnt;
	
	private String fileName;
	
	@Transient
	private MultipartFile uploadFile;
	
	@ManyToOne
	@JoinColumn(name="MEMBER_ID", nullable = false, updatable = false)
	private Member member;
	
	public void setMember(Member member) {
		this.member = member;
		if (!member.getBoardList().contains(this)) {
	        member.getBoardList().add(this);
	    }
	}
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Comment> commentList = new ArrayList<>();

	
}
