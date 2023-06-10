package com.lec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lec.domain.Item;
import com.lec.domain.Member;
import com.lec.domain.PagingInfo;
import com.lec.service.ItemService;

@Controller
@SessionAttributes("pagingInfo")
public class ItemController {
	
	@Autowired
	private ItemService itemService;	
	
	public PagingInfo pagingInfo = new PagingInfo();
	
	@GetMapping("getItemList")
	public String getItemList(Model model,
			@RequestParam(defaultValue="0") int curPage,
			@RequestParam(defaultValue="10") int rowSizePerPage,
			@RequestParam(defaultValue="id") String searchType,
			@RequestParam(defaultValue="") String searchWord) {   		

		Pageable pageable = PageRequest.of(curPage, rowSizePerPage, Sort.by(searchType).ascending());
		Page<Item> pagedResult = itemService.getItemList(pageable, searchType, searchWord);
	
		int totalRowCount  = pagedResult.getNumberOfElements();
		int totalPageCount = pagedResult.getTotalPages();
		int pageSize       = pagingInfo.getPageSize();
		int startPage      = curPage / pageSize * pageSize + 1;
		int endPage        = startPage + pageSize - 1;
		endPage = endPage > totalPageCount ? (totalPageCount > 0 ? totalPageCount : 1) : endPage;
		
//		if (endPage > totalPageCount) {
//			if(totalPageCount > 0) endPage = totalPageCount; else endPage = 1;
//		} 
	
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
		
		return "item/getItemList";
	}
	
	@GetMapping("/insertItem")
	public String insertItemForm(Item item, @ModelAttribute("member") Member member) {
		item.setMember(member);
		System.out.println("-----------" + member.getName());
		if (item.getId() == null) {
			return "redirect:login";
		}
		return "item/insertItem";
	}
	
	@PostMapping("/insertItem")
	public String insertItem(Item item) {
		if (item.getId() == null) {
			return "redirect:login";
		}
		itemService.insertItem(item);
		return "redirect:getItemList";
	}
	
	@GetMapping("deleteItem")
	public String deleteItem(Item item) {
		
		if (item.getId() == null) {
			return "redirect:login";
		}
		itemService.deleteItem(item);
		return "forward:getItemList";		
	}

	@GetMapping("updateItem")
	public String updateItem(Item item, Model model) {
		if (item.getId() == null) {
			return "redirect:login";
		}
		model.addAttribute("item", itemService.getItem(item));	
		return "item/updateItem";
	}
	
	@PostMapping("updateItem")
	public String updateItem(Item item) {
		if (item.getId() == null) {
			return "redirect:login";
		}
		itemService.updateItem(item);	
		return "redirect:getItemList?curPage=" + pagingInfo.getCurPage() + "&rowSizePerPage=" + pagingInfo.getRowSizePerPage()
		                           + "&searchType=" + pagingInfo.getSearchType() + "&searchWord=" + pagingInfo.getSearchWord();
	}	
}
