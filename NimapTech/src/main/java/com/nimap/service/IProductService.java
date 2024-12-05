package com.nimap.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.*;

import com.nimap.bean.ProductBean;

public interface IProductService 
{ 
	
	public Page<ProductBean> getAllDeletedProducts(PageRequest pageable);
	public String saveProduct(ProductBean product);
	
	public String updateproduct(Integer id,ProductBean product);
	
	public String deleteProduct(Integer id);
	
	public ProductBean getProductById(Integer id);
	
	public Page<ProductBean> getAllProduct(PageRequest pageable);


	
	
	
}
