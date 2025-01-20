package tech.csm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "categories")
@Data
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "id")
	@JsonProperty("id")
	private Integer categoryId;
	
	@Column(name = "category")
	private String categoryName;
	
	@Column(name = "parent_category_id")
	private Integer parentCategoryId;

}
