package com.lec.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.context.annotation.DependsOn;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude="boardList")
@Entity
public class Member {
	
	@Id
	@Column(name="member_id")
	private String memberId;
	
	private String password;
	private String name;
	private String age;
	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")
	private Long m_cnt;
	
	private String gender;

	@OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
	private List<Board> boardList = new ArrayList<>();
}
