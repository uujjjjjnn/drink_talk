package com.lec.impl;

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

	@Override
	public Page<Board> getBoardList(Pageable pageable, String searchType, String searchWord) {
		if(searchType.equalsIgnoreCase("type")) {
			return boardRepo.findByTypeContaining(searchWord, pageable);
		} else if(searchType.equalsIgnoreCase("score")) {
			return boardRepo.findByScoreContaining(searchWord, pageable);
		} else {
			return boardRepo.findByCommentContaining(searchWord, pageable);
		}
	}

	@Override
	public void insertBoard(Board board) {
		boardRepo.save(board);
	}

	@Override
	public void updateBoard(Board board) {
		Board findBoard = boardRepo.findById(board.getSeq()).get();

		findBoard.setScore(board.getScore());
		findBoard.setComment(board.getComment());
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

}
