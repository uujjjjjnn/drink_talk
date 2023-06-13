package com.lec.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lec.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	
	@Modifying
	@Transactional
	@Query("update Member m set m.m_cnt = m.m_cnt + 1 where m.memberId = :memberId")
	int incrementMemberCnt(@Param("memberId") String memberId);
	
	Page<Member> findByMemberIdContaining(String memberId, Pageable pageable);
    Page<Member> findByNameContaining(String name, Pageable pageable);
}
