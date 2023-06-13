package com.lec.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.lec.domain.Board;
import com.lec.domain.Member;
import com.lec.domain.PagingInfo;
import com.lec.service.BoardService;

@Controller
@SessionAttributes({"member", "pagingInfo"})
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private Environment environment;
	
	public PagingInfo pagingInfo = new PagingInfo();
	
	@Value("${path.upload}")
	public String uploadFolder;
	
	@ModelAttribute("member")
	public Member setMember() {
		return new Member();
	}
	
	@GetMapping("/getBoardList")
	public String getBoardList(Model model,
			@RequestParam(defaultValue="0") int curPage,
			@RequestParam(defaultValue="10") int rowSizePerPage,
			@RequestParam(defaultValue="title") String searchType,
			@RequestParam(defaultValue="") String searchWord) {
		
		
		Pageable pageable = PageRequest.of(curPage, rowSizePerPage, Sort.by("seq").descending());
		Page<Board> pagedResult = boardService.getBoardList(pageable, searchType, searchWord);
		
		int totalRowCount = pagedResult.getNumberOfElements();
		int totalPageCount = pagedResult.getTotalPages();
		int pageSize = pagingInfo.getPageSize();
		int startPage = curPage / pageSize * pageSize + 1;
		int endPage = startPage + pageSize - 1;
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
	
	@GetMapping("/insertBoard")
	public String insertBoardView(@ModelAttribute("member") Member member) {
		
		if(member.getId() == null) { return "redirect:login"; }
		
		return "board/insertBoard";
	}
/*
	@PostMapping("/insertBoard")
	public String insertBoard(@ModelAttribute("member") Member member, Board board) throws IOException {
		
		if(member.getId() == null) { return "redirect:login"; }
		 
		
		// 파일업로드
		MultipartFile uploadFile = board.getUploadFile();
		if(!uploadFile.isEmpty()) {
			String fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File(uploadFolder + fileName));
			board.setFileName(fileName);
		}
		board.setMember(member);
		
		boardService.insertBoard(board);
		return "redirect:getBoardList";
	}

*/
	@PostMapping("/insertBoard")
	public String insertBoard(@ModelAttribute("member") Member member, @RequestParam("file") MultipartFile uploadFile, Board board) throws IOException {

	    if (member.getId() == null) {
	        return "redirect:login";
	    }

	    // 파일 업로드
	    if (!uploadFile.isEmpty()) {
	        String fileName = uploadFile.getOriginalFilename();
	        uploadFile.transferTo(new File(uploadFolder + fileName));
	        board.setFileName(fileName);
	    }
	    board.setMember(member);

	    boardService.insertBoard(board);
	    return "redirect:getBoardList";
	}

	
	
	
	@GetMapping("/getBoard")
	public String getBoard(@ModelAttribute("member") Member member, Board board, Model model) {
		
		if(member.getId() == null) { return "redirect:login"; }
		
		boardService.updateReadCount(board);
		model.addAttribute("board", boardService.getBoard(board));
		return "board/getBoard";
	}
	/*
	@GetMapping("/updateBoard")
	public String updateBoardView(@ModelAttribute("member") Member member, Board board) {
		
		if(member.getId() == null) { return "redirect:login"; }
		
		board.setMember(member);

		return "board/updateBoard";
	}
	
	@PostMapping("/updateBoard")
	public String updateBoard(@ModelAttribute("member") Member member, Board board) {
		board.setMember(member);
		System.out.println("--------2--------" + board.getTitle());
		
		boardService.updateBoard(board);
		return "forward:getBoardList";
	}
	
	@GetMapping("/deleteBoard")
	public String deleteBoard(@ModelAttribute("member") Member member, Board board) {
		
		board.setMember(member);
		boardService.deleteBoard(board);
		return "forward:getBoardList";
	}
	

	@GetMapping("/updateBoard")
	public String updateBoardView(@RequestParam("seq") Long seq, Model model, Board board) {
		Board boards = boardService.getBoard(board);
		model.addAttribute("board", boards);
		return "board/updateBoard";
	}
*/
	@GetMapping("/updateBoard")
<<<<<<< HEAD
	public String updateBoardView(@RequestParam("seq") Long seq, Model model, Board board) {
	    boardService.getBoard(board);
=======
	public String updateBoardView(@RequestParam("seq") Long seq, Model model) {
	    Board board = boardService.getBoard(seq);
>>>>>>> parent of 18c9f54 (0610 commit)
	    model.addAttribute("board", board);
	    return "board/updateBoard";
	}

	
	
	@PostMapping("/updateBoard")
	public String updateBoard(@ModelAttribute("board") Board board) {
		boardService.updateBoard(board);
		return "redirect:getBoardList";
	}
	
	@GetMapping("/deleteBoard")
	public String deleteBoard(@ModelAttribute("member") Member member, Board board) {
		
		board.setMember(member);
		boardService.deleteBoard(board);
		return "forward:getBoardList";
	}



	
}
