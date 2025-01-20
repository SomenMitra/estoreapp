package tech.csm.model;

import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonProperty("id")
	private Integer productId;

	@JsonProperty("product_name")
	@Column(name = "product_name")
	private String productName;

	
	@JsonProperty("product_description")
	@Column(name = "product_description")
	private String productDescription;

	
	@JsonProperty("price")
	@Column(name = "price")
	private Double productPrice;

	@JsonProperty("ratings")
	@Column(name = "ratings")
	private Integer productRatings;

	@JsonIgnoreProperties({"categoryName","parentCategoryId"})
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Transient
	@JsonIgnore
	private MultipartFile productImgFile;

	@Column(name = "product_img")
	@JsonProperty("product_img")
	private String productImgPath;

}
