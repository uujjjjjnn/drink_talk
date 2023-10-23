package com.lec.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.lec.domain.Board;
import com.lec.domain.Member;
import com.lec.domain.PagingInfo;
import com.lec.service.BoardService;
import com.lec.service.MemberService;

@Controller
@SessionAttributes({"member", "pagingInfo","pagingInfo2"})
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private HttpServletRequest session;
	
	public PagingInfo pagingInfo = new PagingInfo();
	public PagingInfo pagingInfo2 = new PagingInfo();
	
	@ModelAttribute("member")
	public Member setMember() {
		return new Member();
	}
	
	@RequestMapping("/getBoardList")
	public String getBoardList(Model model,
			@RequestParam(defaultValue="0") int curPage,
			@RequestParam(defaultValue="10") int rowSizePerPage,
			@RequestParam(defaultValue="type") String searchType,
			@RequestParam(defaultValue="") String searchWord) {   			
		
		Pageable pageable = PageRequest.of(curPage, rowSizePerPage, Sort.by("seq").descending());
		Page<Board> pagedResult = boardService.getBoardList(pageable, searchType, searchWord);
	
		int totalRowCount  = pagedResult.getNumberOfElements();
		int totalPageCount = pagedResult.getTotalPages();
		int pageSize       = pagingInfo.getPageSize();
		int startPage      = curPage / pageSize * pageSize + 1;
		int endPage        = startPage + pageSize - 1;
		endPage = endPage > totalPageCount ? (totalPageCount > 0 ? totalPageCount : 1) : endPage;
	
		pagingInfo.setCurPage(curPage);
		pagingInfo.setTotalRowCount(totalRowCount);
		pagingInfo.setTotalPageCount(totalPageCount);
		pagingInfo.setStartPage(startPage);
		pagingInfo.setEndPage(endPage);
		pagingInfo.setSearchType(searchType);
		pagingInfo.setSearchWord(searchWord);
		pagingInfo.setRowSizePerPage(rowSizePerPage);
		model.addAttribute("pagingInfo", pagingInfo);

		model.addAttribute("pagedResult", pagedResult);
		model.addAttribute("pageable", pageable);
		model.addAttribute("cp", curPage);
		model.addAttribute("sp", startPage);
		model.addAttribute("ep", endPage);
		model.addAttribute("ps", pageSize);
		model.addAttribute("rp", rowSizePerPage);
		model.addAttribute("tp", totalPageCount);
		model.addAttribute("st", searchType);
		model.addAttribute("sw", searchWord);			
		return "board/getBoardList";
	}
	
	@RequestMapping("/getBoardMyList")
	public String getBoardMyList(Model model, @ModelAttribute("member") Member member,
			@RequestParam(defaultValue="0") int curPage,
			@RequestParam(defaultValue="10") int rowSizePerPage,
			@RequestParam(defaultValue="type") String searchType,
			@RequestParam(defaultValue="") String searchWord) {   			
		
		Pageable pageable = PageRequest.of(curPage, rowSizePerPage, Sort.by("seq").descending());
		
		Member searchMember = new Member();
		searchMember.setMemberId(member.getMemberId());
		Page<Board> pagedResult = boardService.getBoardMyListbyMemberId(pageable, searchType, searchWord, searchMember);
		
		int totalRowCount  = pagedResult.getNumberOfElements();
		
		int totalPageCount = pagedResult.getTotalPages();
		int pageSize       = pagingInfo2.getPageSize();
		int startPage      = curPage / pageSize * pageSize + 1;
		int endPage        = startPage + pageSize - 1;
		endPage = endPage > totalPageCount ? (totalPageCount > 0 ? totalPageCount : 1) : endPage;
		
		pagingInfo2.setCurPage(curPage);
		pagingInfo2.setTotalRowCount(totalRowCount);
		pagingInfo2.setTotalPageCount(totalPageCount);
		pagingInfo2.setStartPage(startPage);
		pagingInfo2.setEndPage(endPage);
		pagingInfo2.setSearchType(searchType);
		pagingInfo2.setSearchWord(searchWord);
		pagingInfo2.setRowSizePerPage(rowSizePerPage);
		model.addAttribute("pagingInfo2", pagingInfo2);
		
		model.addAttribute("pagedResult2", pagedResult);
		model.addAttribute("pageable", pageable);
		model.addAttribute("cp", curPage);
		model.addAttribute("sp", startPage);
		model.addAttribute("ep", endPage);
		model.addAttribute("ps", pageSize);
		model.addAttribute("rp", rowSizePerPage);
		model.addAttribute("tp", totalPageCount);
		model.addAttribute("st", searchType);
		model.addAttribute("sw", searchWord);			
		return "board/getBoardMyList";

		
	}

	@GetMapping("/insertBoard")
	public String insertBoardView(@ModelAttribute("member") Member member) {
		if (member.getMemberId() == null) {
			return "redirect:login";
		}
		boardService.incrementMemberCnt(member);
		return "board/insertBoard";
	}

	@PostMapping("/insertBoard")
	public String insertBoard(@ModelAttribute("member") Member member, Board board) throws IOException {
		if (member.getMemberId() == null) {
			return "redirect:login";
		}	
		board.setMember(member);
		boardService.insertBoard(board);
		return "redirect:getBoardList";
	}
	
	@GetMapping("/insertBoardMy")
	public String insertBoardMyView(@ModelAttribute("member") Member member) {
		if (member.getMemberId() == null) {
			return "redirect:login";
		}
		boardService.incrementMemberCnt(member);
		return "board/insertBoardMy";
	}
	
	@PostMapping("/insertBoardMy")
	public String insertBoardMy(@ModelAttribute("member") Member member, Board board) throws IOException {
		if (member.getMemberId() == null) {
			return "redirect:login";
		}		
		board.setMember(member);
		boardService.insertBoard(board);
		return "redirect:getBoardMyList";
	}

	@GetMapping("/getBoard")
	public String getBoard(@ModelAttribute("member") Member member, Board board, Model model) {
		if (member.getMemberId() == null) {
			return "redirect:login";
		}

		boardService.updateReadCount(board);
		model.addAttribute("board", boardService.getBoard(board));
		return "board/getBoard";
	}

	@PostMapping("/updateBoard")
	public String updateBoard(@ModelAttribute("member") Member member, Board board) {
		if (member.getMemberId() == null) {
			return "redirect:login";
		}

		boardService.updateBoard(board);
		return "forward:getBoardList";
	}
	
	@GetMapping("/getBoardMy")
	public String getBoardMy(@ModelAttribute("member") Member member, Board board, Model model) {
		if (member.getMemberId() == null) {
			return "redirect:login";
		}
		
		model.addAttribute("board", boardService.getBoard(board));
		return "board/getBoardMy";
	}
	
	@PostMapping("/updateBoardMy")
	public String updateBoardMy(@ModelAttribute("member") Member member, Board board) {
		if (member.getMemberId() == null) {
			return "redirect:login";
		}
		
		boardService.updateBoard(board);
		return "forward:getBoardMyList";
	}

	@GetMapping("/deleteBoard")
	public String deleteBoard(@ModelAttribute("member") Member member, Board board) {
		if (member.getMemberId() == null) {
			return "redirect:login";
		}
		
		boardService.deleteBoard(board);
		return "forward:getBoardList";
	}
	
    public int updateView(Board board) {
        return boardService.updateReadCount(board);
    }
}