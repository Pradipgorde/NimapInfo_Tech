package com.nimap.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimap.bean.ProductBean;
import com.nimap.exception.ProductNotFoundException;
import com.nimap.service.IProductService;



@RestController
@RequestMapping("/api/products")
public class ProductController 
{
	 @Autowired
	private IProductService productService;
	
	 
	 @GetMapping()
	 public ResponseEntity<Page<ProductBean>> getAllProduct( @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") int page,
			 @RequestParam(defaultValue = "productName") String sortBy,@RequestParam(defaultValue = "asc") String sortOrder)
	     {
		
		 Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		 
		 PageRequest pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
		    
		    Page<ProductBean> productPage = productService.getAllProduct(pageable);
		    
		    
		    
		    return new ResponseEntity<>(productPage, HttpStatus.OK);
		} 
	 
	 
	
	 @PostMapping()
	public ResponseEntity<String> saveProduct(@RequestBody ProductBean product)
	{
		 
		String msg = productService.saveProduct(product);		
		return new ResponseEntity<String>(msg,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductBean> getProductData(@PathVariable Integer id)
	{
		  
		 ProductBean product = productService.getProductById(id);
		
		 
		return new ResponseEntity<ProductBean>(product,HttpStatus.OK);
	}
	

	
	
	 @DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProuduct(@PathVariable Integer id)
	{
		String msg = productService.deleteProduct(id);
		
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	 
	@PutMapping("/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable Integer id, @RequestBody ProductBean product)
	{
		 
		String msg = productService.updateproduct(id,product);
		
		return new ResponseEntity<String>(msg,HttpStatus.OK);
		
		
	}
	
	
	 @GetMapping("/delete")
	    public ResponseEntity<Page<ProductBean>> getDeletedProducts(
	            @RequestParam(defaultValue = "5") int size, 
	            @RequestParam(defaultValue = "0") int page) {

	        PageRequest pageable = PageRequest.of(page, size);
	        Page<ProductBean> productPage = productService.getAllDeletedProducts(pageable);
	        return new ResponseEntity<>(productPage, HttpStatus.OK);
	    }	
}
