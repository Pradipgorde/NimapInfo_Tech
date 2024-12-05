package com.nimap.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nimap.modal.Category;

public interface ICategoryRepo extends JpaRepository<Category,Integer>
{
	 public List<Category> findByIsDeletedFalse();
	public Category findByCategoryType(String name);
	  public Page<Category>    findByIsDeletedFalse(PageRequest pageable);
	 public  Page<Category> findByIsDeletedTrue(PageRequest pageable);


}
