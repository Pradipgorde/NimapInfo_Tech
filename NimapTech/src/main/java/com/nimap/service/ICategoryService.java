package com.nimap.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.nimap.bean.CategoryBean;
import com.nimap.modal.Category;

public interface ICategoryService 
{
	
	 public Page<CategoryBean> getAllDeletedCategories(PageRequest pageable);
	
	public Page<CategoryBean> getAllCategories(PageRequest pageable);
	
	public CategoryBean getCategoryById(Integer id);
	 public String createCategory(Category category);
	 public String updateCategory(Integer id, Category category);
	 public String deleteCategory(Integer id) ;

}
