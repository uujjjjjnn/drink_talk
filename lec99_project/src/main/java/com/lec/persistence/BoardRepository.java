package com.lec.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lec.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {

	@Modifying
    @Transactional
    @Query("update Board b set b.cnt = b.cnt + 1 where b.seq = :seq")
    int updateReadCount(@Param("seq")Long seq);

    Page<Board> findByTitleContaining(String title, Pageable pageable);
    Page<Board> findByMemberIdContaining(String memberId, Pageable pageable);
    Page<Board> findByContentContaining(String content, Pageable pageable);
	
	
	
}
