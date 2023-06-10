package com.lec.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lec.domain.Item;

public interface ItemService {

	long getTotalRowCount(Item item);
	Item getItem(Item item);
	Page<Item> getItemList(Pageable pageable, String searchType, String searchWord);
	void insertItem(Item item);
	void updateItem(Item item);
	void deleteItem(Item item);
}
