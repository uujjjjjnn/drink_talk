package com.lec.persistence;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lec.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

/*	
	public static Member findMemberWithBoardListAndItemList(String memberId) {
		
		EntityManager entityManager = null;
		
        return entityManager.createQuery(
            "SELECT DISTINCT m FROM Member m " +
            "LEFT JOIN FETCH m.boardList " +
            "LEFT JOIN FETCH m.itemList " +
            "WHERE m.id = :memberId", Member.class)
            .setParameter("memberId", memberId)
            .getSingleResult();
    }
*/	
	Page<Member> findByIdContaining(String id, Pageable pageable);
	Page<Member> findByNameContaining(String name, Pageable pageable);
}
