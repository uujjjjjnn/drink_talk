package com.lec.domain;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Detail {
	
	private Double flavorSweet; // 단맛
	private Double flavorBody; // 바디감
	private Double flavorPop; // 청량감
	private Double flavorSour; // 신맛

	
	
}
