package com.lec.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.domain.Detail;

public interface DetailRepository extends JpaRepository<Detail, Long> {

	Page<Detail> findByTypeContaining(String type, Pageable pageable);
    Page<Detail> findByItemNameContaining(String itemName, Pageable pageable);	
}
