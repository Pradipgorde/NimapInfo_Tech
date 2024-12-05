package com.nimap.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nimap.bean.ProductBean;
import com.nimap.dao.ICategoryRepo;
import com.nimap.dao.IProductRepo;
import com.nimap.exception.ProductNotFoundException;
import com.nimap.modal.Category;
import com.nimap.modal.Product;

@Service
public class ProductService implements IProductService
{


	@Autowired
	private IProductRepo productRepo;
	@Autowired
	private ICategoryRepo categoryRepo;
	
	
	@Override
	public String saveProduct(ProductBean product) 
	{
		Product myProduct = new Product();
		   
		 System.out.println("cat name:"+product.getCategoryType().toUpperCase()+" "+product.getProductName());
		 Category category = categoryRepo.findByCategoryType(product.getCategoryType().toUpperCase());
		 if(category==null)
		 {
			  
			 return "Your Product Category Does Not Exist";
		 }
		
	 	myProduct.setProductName(product.getProductName());
		myProduct.setProductPrice(product.getProductPrice());
		  myProduct.setCategory(category);
		
		  Product save = productRepo.save(myProduct);
		  
		
		
		return "Product Saved With Id "+save.getProductId();
	}

	@Override
	public String updateproduct(Integer id,ProductBean product) 
	{
	
	    Optional<Product> findById = productRepo.findById(product.getProductId());
	      if(findById.isPresent())
	      {
	    	
	    	  Product myProduct = new Product();
	  		
	 		 Category category = categoryRepo.findByCategoryType(product.getCategoryType().toUpperCase());
	 		 if(category==null)
	 		 {
	 			 
	 			 return "Your Product Category Does Not Exist";
	 		 }
	 		
	 		 myProduct.setProductId(product.getProductId());
	 		 myProduct.setProductName(product.getProductName());
	 		myProduct.setProductPrice(product.getProductPrice());
	 		  myProduct.setCategory(category);
	 		
	 		  Product save = productRepo.save(myProduct);
	 		  
	 		
	 		
	 		return "Product updated  With Id "+save.getProductId();
	    	  
	    	  
	    	  
	      }else
		
		return "Product Not Found..";
	}

	@Override
	public String deleteProduct(Integer id) 
	{
	
		Optional<Product> findById = productRepo.findById(id);
		  if(findById.isPresent()) 
		  {
			  Product productToDelete = findById.get();
	            productToDelete.setIsDeleted(true); 
	            productRepo.save(productToDelete);
			   return "Produtct Deleted Successfull for Id:"+id;
		  }
		  else
		return "Product Is not Found With Id:"+id;
	}

	@Override
	public ProductBean getProductById(Integer id) 
	{
	       
		 Optional<Product> findById = productRepo.findById(id);
		 if(findById.isPresent())
		 {
			 Product product = findById.get();
			  
			ProductBean pb = new ProductBean();
			if(!product.getIsDeleted())
			{
				pb.setProductId(product.getProductId()); pb.setProductName(product.getProductName());
				pb.setProductPrice(product.getProductPrice()); pb.setCategoryType(product.getCategory().getCategoryType().toUpperCase());	

				return pb; 
			}else throw new ProductNotFoundException("Product not available for the given ID: " +id);
			
			
			 
		 }else
				
	       throw new ProductNotFoundException("Product not available for the given ID: " +id);
	}

	
	@Override
	public Page<ProductBean> getAllProduct(PageRequest pageable) {
	    Page<Product> productPage = productRepo.findAll(pageable);

	    // Filter products to exclude deleted ones and map them to ProductBean
	    List<ProductBean> productBeans = productPage.getContent().stream()
	        .filter(product -> !product.getIsDeleted())  // Filter out deleted products
	        .map(product -> {
	            ProductBean pb = new ProductBean();
	            pb.setProductId(product.getProductId());
	            pb.setProductName(product.getProductName());
	            pb.setProductPrice(product.getProductPrice());
	            pb.setCategoryType(product.getCategory().getCategoryType().toUpperCase());
	            return pb;
	        })
	        .collect(Collectors.toList());

	   
	    if (productBeans.isEmpty()) {
	        throw new ProductNotFoundException("Product Not Available");
	    }

	   
	    return new PageImpl<>(productBeans, pageable, productBeans.size());
	}

	
	@Override
	public Page<ProductBean> getAllDeletedProducts(PageRequest pageable) {
	    Page<Product> productPage = productRepo.findByIsDeletedTrue(pageable);

	   
	    if (productPage.getContent().isEmpty()) {
	      
	    	   throw new ProductNotFoundException("Product Not Available");
	    }

	   
	    Page<ProductBean> productBeanPage = productPage.map(product -> {
	        ProductBean pb = new ProductBean();
	        pb.setProductId(product.getProductId());
	        pb.setProductName(product.getProductName());
	        pb.setProductPrice(product.getProductPrice());
	        pb.setCategoryType(product.getCategory().getCategoryType().toUpperCase());
	        return pb;
	    });

	    return productBeanPage;
	}

	

}
