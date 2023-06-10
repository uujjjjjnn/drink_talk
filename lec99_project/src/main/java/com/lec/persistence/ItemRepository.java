package com.lec.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.lec.domain.Board;
import com.lec.domain.Item;

public interface ItemRepository extends CrudRepository<Item, String> {

	Page<Item> findByIdContaining(String id, Pageable pageable);
    Page<Item> findByTypeContaining(String type, Pageable pageable);


}
