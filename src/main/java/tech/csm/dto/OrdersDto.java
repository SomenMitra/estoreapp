package tech.csm.dto;

import java.util.List;
import lombok.Data;

@Data
public class OrdersDto {

	private Integer userId;

	private String userName;

	private String address;
	
	private String city;

	private String state;

	private String pin;

	private Double total;

	private String userEmail;

	List<ProductDto> orderDetails;

}
