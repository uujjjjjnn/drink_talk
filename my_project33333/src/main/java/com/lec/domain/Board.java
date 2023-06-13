package com.lec.domain;

import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;

@Getter
@Setter
@Entity
public class Board {

	@Id @GeneratedValue
	private Long b_seq;
	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")
	private Long b_cnt;	
	
	private String type;
	
	private String itemName;
	
	@OneToOne(mappedBy = "board")
	private Detail detail;
	
}
