package com.lec.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Detail {
	
	@Id @GeneratedValue
	private Double flavorId;
	
	private Double flavorSweet; // 단맛
	private Double flavorBody; // 바디감
	private Double flavorPop; // 청량감
	private Double flavorSour; // 신맛

	@OneToOne
	@JoinColumn(name="seq", nullable = false, updatable = false)
	private Board board;
	
}
