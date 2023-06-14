package com.lec.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lec.domain.Board;
import com.lec.domain.Member;
import com.lec.persistence.BoardRepository;
import com.lec.persistence.MemberRepository;
import com.lec.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardRepository boardRepo; 
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Override
	public long getTotalRowCount(Board board) {
		return boardRepo.count();
	}

	@Override
	public Board getBoard(Board board) {
		Optional<Board> findBoard = boardRepo.findById(board.getSeq());
		if(findBoard.isPresent())
			return findBoard.get();
		else return null;
	}

	//게시글 조회
	@Override
	public Page<Board> getBoardList(Pageable pageable, String searchType, String searchWord) {
		if(searchType.equalsIgnoreCase("type")) {
			return boardRepo.findByTypeContaining(searchWord, pageable);
		} else if(searchType.equalsIgnoreCase("itemName")) {
			return boardRepo.findByItemNameContaining(searchWord, pageable);
		} else {
			return boardRepo.findByTag1ContainingOrTag2ContainingOrTag3Containing(searchWord, searchWord, searchWord, pageable);
		}
	}
	
	//현재 세션 유저정보 기준으로 조회
	@Override
	public Page<Board> getBoardMyListbyMemberId(Pageable pageable, String searchType, String searchWord, Member member) {
		if(searchType.equalsIgnoreCase("type")) {
			return boardRepo.findByTypeContainingAndMember(searchWord, member, pageable);
		} else if(searchType.equalsIgnoreCase("itemName")) {
			return boardRepo.findByItemNameContainingAndMember(searchWord, member, pageable);
		} else {
			return boardRepo.findByTag1ContainingOrTag2ContainingOrTag3ContainingAndMember(searchWord, searchWord, searchWord, member, pageable);
		}
	}
			

	@Override
	public void insertBoard(Board board) {
		boardRepo.save(board);
	}

	@Override
	public void updateBoard(Board board) {
		Board findBoard = boardRepo.findById(board.getSeq()).get();
		
		findBoard.setFlavorBody(board.getFlavorBody());
		findBoard.setFlavorPop(board.getFlavorPop());
		findBoard.setFlavorSour(board.getFlavorSour());
		findBoard.setFlavorSweet(board.getFlavorSweet());
		
		findBoard.setComment(board.getComment());
		findBoard.setScore(board.getScore());
		findBoard.setDegree(board.getDegree());
		findBoard.setTag1(board.getTag1());
		findBoard.setTag2(board.getTag2());
		findBoard.setTag3(board.getTag3());
		boardRepo.save(findBoard);
	}

	@Override
	public void deleteBoard(Board board) {
		boardRepo.deleteById(board.getSeq());
	}

	@Override
	public int updateReadCount(Board board) {
		return boardRepo.updateReadCount(board.getSeq());
	}

	@Override
	public int incrementMemberCnt(Member member) {
		return boardRepo.incrementMemberCnt(member.getMemberId());
	}

	
//	@Override
//	public Page<Board> getBoardMyList(Pageable pageable, String searchType, String searchWord) {
//		if(searchType.equalsIgnoreCase("type")) {
//			return boardRepo.findByTypeContaining(searchWord, pageable);
//		}
//		else {
//			return boardRepo.findByItemNameContaining(searchWord, pageable);
//		}
//	}
	
	//



}
