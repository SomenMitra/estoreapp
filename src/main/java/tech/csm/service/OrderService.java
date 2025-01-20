package tech.csm.service;

import java.util.List;
import java.util.Map;

import tech.csm.dto.OrdersDto;
import tech.csm.model.UserModel;

public interface OrderService {

	List<Map<String, Object>> placeOrder(UserModel user, OrdersDto order);

	List<Map<String, Object>> getAllOrderList(Integer userId);

	List<Map<String, Object>> getOrderProductsList(Integer orderId);

}
