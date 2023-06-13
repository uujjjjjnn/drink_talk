package com.lec.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude="detailList")
@Entity
public class Member {

	@Id
	@Column(name="member_id")
	private String memberId;
	
	private String password;
	private String name;
	private String age;
	private String gender;
	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")
	private Long m_cnt;
	
	@OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
	private List<Detail> detailList = new ArrayList<>();
	
	
}
