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
	
	@Modifying
	@Transactional
	@Query("update Member m set m.m_cnt = m.m_cnt + 1 where m.memberId = :memberId")
	int incrementMemberCnt(@Param("memberId") String memberId);

	
	Page<Board> findByTypeContaining(String type, Pageable pageable);
    Page<Board> findByScoreContaining(String score, Pageable pageable);
    Page<Board> findByCommentContaining(String comment, Pageable pageable);

}
