package tech.csm.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductDto {
	
	private Integer productId;
	
	private Integer qty;
	
	private Double price;
	
	private Double amount;

}
