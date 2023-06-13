package com.lec.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lec.domain.Detail;
import com.lec.persistence.DetailRepository;
import com.lec.service.DetailService;

@Service
public class DetailServiceImpl implements DetailService {

	@Autowired
	private DetailRepository detailRepo;
	
	@Override
	public long getTotalRowCount(Detail detail) {
		return detailRepo.count();
	}

	@Override
	public Detail getDetail(Detail detail) {
		Optional<Detail> findDetail = detailRepo.findById(detail.getD_seq());
		if(findDetail.isPresent()) 
			return findDetail.get();
		else return null;
	}

	@Override
	public Page<Detail> getDetailList(Pageable pageable, String searchType, String searchWord) {
		if(searchType.equalsIgnoreCase("type")) {
			return detailRepo.findByTypeContaining(searchType, pageable);
		} else {
			return detailRepo.findByItemNameContaining(searchWord, pageable);
		}
	}

	@Override
	public void insertDetail(Detail detail) {
		detailRepo.save(detail);
	}

	@Override
	public void updateDetail(Detail detail) {
		detailRepo.save(detail);
	}

	@Override
	public void deleteDetail(Detail detail) {
		detailRepo.deleteById(detail.getD_seq());
	}

}
