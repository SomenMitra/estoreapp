package tech.csm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import tech.csm.model.Orders;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Integer> {

	@Modifying
	@Transactional
	@Query(value = 
	"INSERT INTO orderdetails (order_id, productId, qty, price, amount) "
+ "VALUES (:orderId, :productId, :qty, :price, :amount)"
			, nativeQuery = true)
	void saveInOrderDetails(@Param("orderId") Integer orderId, @Param("productId") Integer prodId,
			@Param("qty") Integer quantity, @Param("price") Double price, @Param("amount") Double amount);

	@Query(value = 
		    "SELECT order_id AS orderId, date_format(order_date, '%d/%m/%Y') AS orderDate, " +
		    "user_name as userName, address, city, state, pin, total " +
		    "FROM orders WHERE user_id = :userId",
		    nativeQuery = true)
		List<Map<String, Object>> getAllOrderList(@Param("userId") Integer userId);

	
	@Query(value = "select orderdetails.*, products.product_name as productName, products.product_img as productImg from orderdetails, products "
			+ "where orderdetails.productId = products.id and order_id = :orderId",
			nativeQuery = true)
	List<Map<String, Object>> getOrderProductsList(@Param("orderId") Integer orderId);


}
