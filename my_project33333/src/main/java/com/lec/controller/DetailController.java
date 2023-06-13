package com.lec.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.lec.domain.Detail;
import com.lec.domain.Member;
import com.lec.domain.PagingInfo;
import com.lec.service.DetailService;

@Controller
@SessionAttributes({"member", "pagingInfoDetail"})
public class DetailController {

	@Autowired
	private DetailService detailService;
	
	@Autowired
	private Environment environment;
	
	public PagingInfo pagingInfoDetail = new PagingInfo();
	
	@ModelAttribute("member")
	public Member setMember() {
		return new Member();
	}
	
	@RequestMapping("/getDetailList")
	public String getDetailList(Model model,
			@RequestParam(defaultValue="0") int curPage,
			@RequestParam(defaultValue="10") int rowSizePerPage,
			@RequestParam(defaultValue="type") String searchType,
			@RequestParam(defaultValue="") String searchWord) {   			
		
		Pageable pageable = PageRequest.of(curPage, rowSizePerPage, Sort.by("d_seq").descending());
		Page<Detail> pagedResult = detailService.getDetailList(pageable, searchType, searchWord);
	
		int totalRowCount  = pagedResult.getNumberOfElements();
		int totalPageCount = pagedResult.getTotalPages();
		int pageSize       = pagingInfoDetail.getPageSize();
		int startPage      = curPage / pageSize * pageSize + 1;
		int endPage        = startPage + pageSize - 1;
		endPage = endPage > totalPageCount ? (totalPageCount > 0 ? totalPageCount : 1) : endPage;
	
		pagingInfoDetail.setCurPage(curPage);
		pagingInfoDetail.setTotalRowCount(totalRowCount);
		pagingInfoDetail.setTotalPageCount(totalPageCount);
		pagingInfoDetail.setStartPage(startPage);
		pagingInfoDetail.setEndPage(endPage);
		pagingInfoDetail.setSearchType(searchType);
		pagingInfoDetail.setSearchWord(searchWord);
		pagingInfoDetail.setRowSizePerPage(rowSizePerPage);
		model.addAttribute("pagingInfoDetail", pagingInfoDetail);

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
		return "detail/getDetailList";
	}
	
	@GetMapping("/insertDetail")
	public String insertDetailView(@ModelAttribute("member") Member member) {
		if (member.getMemberId() == null) {
			return "redirect:login";
		}
		return "detail/insertDetail";
	}

	@PostMapping("/insertDetail")
	public String insertDetail(@ModelAttribute("member") Member member, Detail detail) throws IOException {
		if (member.getMemberId() == null) {
			return "redirect:login";
		}	
		detail.setMember(member);
		detailService.insertDetail(detail);
		return "redirect:getDetailList";
	}
	
	@GetMapping("/getDetail")
	public String getDetail(@ModelAttribute("member") Member member, Detail detail, Model model) {
		if (member.getMemberId() == null) {
			return "redirect:login";
		}

		// detailService.updateReadCount(detail);
		model.addAttribute("detail", detailService.getDetail(detail));
		return "detail/getDetail";
	}

	@PostMapping("/updateDetail")
	public String updateDetail(@ModelAttribute("member") Member member, Detail detail) {
		if (member.getMemberId() == null) {
			return "redirect:login";
		}

		detailService.updateDetail(detail);
		return "forward:getDetailList";
	}
	
	@GetMapping("/deleteDetail")
	public String deleteDetail(@ModelAttribute("member") Member member, Detail detail) {
		if (member.getMemberId() == null) {
			return "redirect:login";
		}
		
		detailService.deleteDetail(detail);
		return "forward:getDetailList";
	}

}
