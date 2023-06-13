package com.lec.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lec.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	@Modifying
    @Transactional
    @Query("update Board b set b.b_cnt = b.b_cnt + 1 where b.b_seq = :b_seq")
    int updateReadCount(@Param("b_seq")Long b_seq);
	
	Page<Board> findByTypeContaining(String type, Pageable pageable);
	Page<Board> findByItemNameContaining(String itemName, Pageable pageable);
}
