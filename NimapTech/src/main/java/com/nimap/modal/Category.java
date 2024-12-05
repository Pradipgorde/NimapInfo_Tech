package com.nimap.modal;



import java.io.Serializable;
import java.util.List;

import org.springframework.lang.NonNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name="CategoryTable")

 @Data

public class Category  implements Serializable
{
	
	
	
	public Category() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@SequenceGenerator(name="gen1",sequenceName="Seq_catId",initialValue=1000,allocationSize=1)
	@GeneratedValue(generator="gen1",strategy=GenerationType.SEQUENCE)
	@Column(length=20)
	private Integer categoryId;
	
	
	@NonNull
	@Column(length=30)
	private String categoryType;
	
	@Column(nullable = false, columnDefinition = "NUMBER(1) DEFAULT 0")
    private Boolean isDeleted = false;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Product> product;
     
	 
 	public void setIsDeleted(Boolean b) 
	{
		  this.isDeleted=b;
	}
	 
	

	
	
	

}
