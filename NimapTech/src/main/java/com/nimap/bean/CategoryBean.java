package com.nimap.bean;

import java.util.List;

import lombok.Data;

@Data
public class CategoryBean 
{
	
	
	private Integer categoryId;
	private String categoryType;
	
	private List<ProductDTO> product;
	

}
