package com.nimap.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nimap.modal.Product;

public interface IProductRepo extends JpaRepository<Product,Integer>
{
	
	public List<Product> findByIsDeletedFalse();
	public Page<Product> findByIsDeletedTrue(PageRequest pageable);


}
