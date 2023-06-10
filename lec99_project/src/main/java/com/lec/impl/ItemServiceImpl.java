package com.lec.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lec.domain.Item;
import com.lec.persistence.ItemRepository;
import com.lec.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository itemRepo;
	
	@Override
	public long getTotalRowCount(Item item) {
		return itemRepo.count();
	}

	@Override
	public Item getItem(Item item) {
		Optional<Item> findItem = itemRepo.findById(item.getId());
		if(findItem.isPresent())
			return findItem.get();
		else return null;	}

	@Override
	public Page<Item> getItemList(Pageable pageable, String searchType, String searchWord) {
		if(searchType.equalsIgnoreCase("id")) {
			return itemRepo.findByIdContaining(searchWord, pageable);
		} else {
			return itemRepo.findByTypeContaining(searchWord, pageable);
		}
	}

	@Override
	public void insertItem(Item item) {
		itemRepo.save(item);
	}

	@Override
	public void updateItem(Item item) {
		Item findItem = itemRepo.findById(item.getId()).get();
		
		findItem.setFlavorBody(item.getFlavorBody());
		findItem.setFlavorPop(item.getFlavorPop());
		findItem.setFlavorSour(item.getFlavorSour());
		findItem.setFlavorSweet(item.getFlavorSweet());
		findItem.setScore(item.getScore());
		findItem.setComment(item.getComment());
		itemRepo.save(findItem);
	}

	@Override
	public void deleteItem(Item item) {
		itemRepo.deleteById(item.getId());
	}

}
