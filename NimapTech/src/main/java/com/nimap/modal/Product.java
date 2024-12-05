package com.nimap.modal;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@SuppressWarnings("serial")
@Entity
@Table(name="ProductTable")
@Data
public class Product implements Serializable
{
	
	
	
	@Id
	@Column(length=20)
    @SequenceGenerator(name="gen",sequenceName="Seq_productId",initialValue=100,allocationSize=1)
	@GeneratedValue(generator="gen",strategy=GenerationType.SEQUENCE)
	private Integer productId;
	
	@Column(length=30)
	private String productName;
	
	@Column(length=20)
	private Double productPrice;
	
	@Column(nullable = false, columnDefinition = "NUMBER(1) DEFAULT 0")
    private Boolean isDeleted = false; 
	
	@ManyToOne(targetEntity = Category.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", referencedColumnName = "categoryId")
	private Category category;

	public void setIsDeleted(Boolean b)
	{
	  this.isDeleted=b;
		
	}
	
	
	

}
