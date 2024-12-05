package com.nimap.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import com.nimap.bean.CategoryBean;
import com.nimap.modal.Category;
import com.nimap.service.ICategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

   
    @GetMapping
    public ResponseEntity<Page<CategoryBean>> getAllCategories(@RequestParam(defaultValue = "5") int size,  @RequestParam(defaultValue = "0") int page)
    {
        PageRequest pageable = PageRequest.of(page, size);
        
        Page<CategoryBean> categoryPage = categoryService.getAllCategories(pageable);
        
        return new ResponseEntity<>(categoryPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryBean> getCategoryById(@PathVariable Integer id)
    {
    	CategoryBean category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    
    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        String message = categoryService.createCategory(category);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        String message = categoryService.updateCategory(id, category);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        String message = categoryService.deleteCategory(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @GetMapping("/delete")
    public ResponseEntity<Page<CategoryBean>> getDeletedCategories(
            @RequestParam(defaultValue = "5") int size,  
            @RequestParam(defaultValue = "0") int page) {

        PageRequest pageable = PageRequest.of(page, size);
        Page<CategoryBean> categoryPage = categoryService.getAllDeletedCategories(pageable);
        return new ResponseEntity<>(categoryPage, HttpStatus.OK);
    }
    
}
