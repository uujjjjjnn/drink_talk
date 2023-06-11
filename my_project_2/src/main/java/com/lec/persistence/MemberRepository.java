package com.lec.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	
	Page<Member> findByMemberIdContaining(String memberId, Pageable pageable);
    Page<Member> findByNameContaining(String name, Pageable pageable);
	
}
