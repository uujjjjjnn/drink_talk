package com.lec.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lec.domain.Board;
import com.lec.domain.Member;

public interface BoardRepository extends CrudRepository<Board, Long> {

	@Modifying
    @Transactional
    @Query("update Board b set b.cnt = b.cnt + 1 where b.seq = :seq")
    int updateReadCount(@Param("seq")Long seq);
	
	@Modifying
	@Transactional
	@Query("update Member m set m.m_cnt = m.m_cnt + 1 where m.memberId = :memberId")
	int incrementMemberCnt(@Param("memberId") String memberId);
/*
	@Modifying
	@Transactional
	@Query("select b from Board b where b.memberId = :memberId")
	List<Board> getBoardByListMemberId(@Param("memberId") String memberId);
*/	
	
	Page<Board> findByTypeContaining(String type, Pageable pageable);
    Page<Board> findByItemNameContaining(String itemName, Pageable pageable);
    Page<Board> findByTypeContainingAndMember(String searchWord, Member member, Pageable pageable);
    Page<Board> findByItemNameContainingAndMember(String searchWord, Member member, Pageable pageable);
    Page<Board> findByTag1ContainingOrTag2ContainingOrTag3Containing(String searchWord, String searchWord2, String searchWord3, Pageable pageable);
    Page<Board> findByTag1ContainingOrTag2ContainingOrTag3ContainingAndMember(String searchWord, String searchWord2, String searchWord3, Member member, Pageable pageable);



}
