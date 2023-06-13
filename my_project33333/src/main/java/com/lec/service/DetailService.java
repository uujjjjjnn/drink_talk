package com.lec.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lec.domain.Detail;

public interface DetailService {

	long getTotalRowCount(Detail detail);
	Detail getDetail(Detail detail);
	Page<Detail> getDetailList(Pageable pageable, String searchType, String searchWord);
	void insertDetail(Detail detail);
	void updateDetail(Detail detail);
	void deleteDetail(Detail detail);

}
