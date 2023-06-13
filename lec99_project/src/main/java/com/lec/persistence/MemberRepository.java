package com.lec.persistence;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.lec.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

	Page<Member> findByNickContaining(String nick, Pageable pageable);
	Page<Member> findByRoleContaining(String role, Pageable pageable);
	Page<Member> findByRegionContaining(String region, Pageable pageable);
	Optional<Member> findByNick(String id);

}
