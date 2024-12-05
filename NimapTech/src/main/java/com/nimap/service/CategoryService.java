package com.nimap.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nimap.bean.CategoryBean;
import com.nimap.bean.ProductDTO;
import com.nimap.dao.ICategoryRepo;
import com.nimap.dao.IProductRepo;
import com.nimap.modal.Category;
import com.nimap.modal.Product;

@Service
public class CategoryService implements ICategoryService
{

    @Autowired
    private ICategoryRepo categoryRepo;
    
    @Autowired
    private IProductRepo productRepo;

    @Override
    public Page<CategoryBean> getAllCategories(PageRequest pageable) {
    	                  
        Page<Category> categoryPage = categoryRepo.findByIsDeletedFalse(pageable);
        
        
        List<CategoryBean> categoryBeans = new ArrayList<>();
        
        
        for (Category category : categoryPage.getContent()) {
            CategoryBean categoryBean = new CategoryBean();
            categoryBean.setCategoryId(category.getCategoryId());
            categoryBean.setCategoryType(category.getCategoryType().toUpperCase());
            
          
            List<ProductDTO> productDTOList = new ArrayList<>();
            
          
            for (Product product : category.getProduct()) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setProductId(product.getProductId());
                productDTO.setProductName(product.getProductName());
                productDTO.setProductPrice(product.getProductPrice());
                productDTOList.add(productDTO);
            }
            
            categoryBean.setProduct(productDTOList);
            
            
            categoryBeans.add(categoryBean);
        }
          return new PageImpl<>(categoryBeans, pageable, categoryPage.getTotalElements());
    }

    
    
    
    @Override
    public CategoryBean getCategoryById(Integer id) 
    {
        Optional<Category> category = categoryRepo.findById(id);
        
        if (category.isPresent()) {
            Category category2 = category.get();
            
            CategoryBean cb = new CategoryBean();
                                          
           System.out.println(category2.getCategoryType()+" "+category2.getCategoryId());
           
            cb.setCategoryId(id); cb.setCategoryType(category2.getCategoryType());
            List<Product> product = category2.getProduct();
            Iterator<Product> iterator = product.iterator();
            List<ProductDTO> productList = new ArrayList<>();
            
            while(iterator.hasNext())
            {
            	Product pd = iterator.next();
                 
            	ProductDTO dto= new ProductDTO();
            	
            	dto.setProductId(pd.getProductId()); dto.setProductName(pd.getProductName()); dto.setProductPrice(pd.getProductPrice());
                     	
            	productList.add(dto);
            }
            cb.setProduct(productList);
            
            
            
            return cb;
        } else {
            throw new RuntimeException("Category not found with id " + id); 
        }
    }

    @Override
    public String createCategory(Category category)
    {                
        category.setCategoryType(category.getCategoryType().toUpperCase());
        Category savedCategory = categoryRepo.save(category);
        return "Category created with ID: " + savedCategory.getCategoryId();
    }

    @Override
    public String updateCategory(Integer id, Category category)
    {
        Optional<Category> existingCategory = categoryRepo.findById(id);
        if (existingCategory.isPresent()) {
        	        
        	Category category2 = existingCategory.get();
        	 
        	category2.setCategoryType(category.getCategoryType().toUpperCase());
        	
            categoryRepo.save(category2);
            return "Category updated with ID: " + id;
        } else {
            return "Category not found with id " + id;
        }
    }

    @Override
    public String deleteCategory(Integer id) 
    {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isPresent()) 
        {
        	  Category categoryToDelete = category.get();
              categoryToDelete.setIsDeleted(true);  
              categoryRepo.save(categoryToDelete);

              for (Product product : categoryToDelete.getProduct()) 
              {
                  product.setIsDeleted(true);
                  productRepo.save(product);
              }  
        	   
            return "Category deleted with ID: " + id;
        } else {
            return "Category not found with id " + id;
        }
    }
    
    @Override
    public Page<CategoryBean> getAllDeletedCategories(PageRequest pageable) {
        
        Page<Category> categoryPage = categoryRepo.findByIsDeletedTrue(pageable);
        
        List<CategoryBean> categoryBeans = new ArrayList<>();
        
        for (Category category : categoryPage.getContent()) {
            CategoryBean categoryBean = new CategoryBean();
            categoryBean.setCategoryId(category.getCategoryId());
            categoryBean.setCategoryType(category.getCategoryType().toUpperCase());

            List<ProductDTO> productDTOList = new ArrayList<>();
            for (Product product : category.getProduct()) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setProductId(product.getProductId());
                productDTO.setProductName(product.getProductName());
                productDTO.setProductPrice(product.getProductPrice());
                productDTOList.add(productDTO);
            }

            categoryBean.setProduct(productDTOList);
            categoryBeans.add(categoryBean);
        }

        return new PageImpl<>(categoryBeans, pageable, categoryPage.getTotalElements());
    }

    
}
