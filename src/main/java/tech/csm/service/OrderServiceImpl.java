package tech.csm.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tech.csm.dto.OrdersDto;
import tech.csm.dto.ProductDto;
import tech.csm.model.Orders;
import tech.csm.model.UserModel;
import tech.csm.repo.OrderRepo;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepo orderRepo;

	@Transactional
	@Override
	public List<Map<String, Object>> placeOrder(UserModel user, OrdersDto order) {
		
		Orders ordersEntity = new Orders();
		ordersEntity.setUserName(order.getUserName());
		ordersEntity.setUserId(user.getUserId());
		ordersEntity.setAddress(order.getAddress());
		ordersEntity.setCity(order.getCity());
		ordersEntity.setState(order.getState());
		ordersEntity.setPin(order.getPin());
		ordersEntity.setTotal(order.getTotal());
		Orders orderSaved = orderRepo.save(ordersEntity);
		
		for (int i = 0; i < order.getOrderDetails().size(); i++) {
			Integer orderId = orderSaved.getOrderId();
			Integer prodId = order.getOrderDetails().get(i).getProductId();
			Integer quantity = order.getOrderDetails().get(i).getQty();
			Double price = order.getOrderDetails().get(i).getPrice();
			Double amount = order.getOrderDetails().get(i).getAmount();
			orderRepo.saveInOrderDetails(orderId,prodId,quantity,price,amount);
		}
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap.put("status","Success");
		objMap.put("msg", "Products purchased!");
		List<Map<String, Object>> resList = new ArrayList<>();
		resList.add(objMap);
		return resList;
	}

	@Override
	public List<Map<String, Object>> getAllOrderList(Integer userId) {
		try {
			List<Map<String, Object>> orderListMap = orderRepo.getAllOrderList(userId);
			return orderListMap;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> getOrderProductsList(Integer orderId) {
		try {
			List<Map<String, Object>> orderProductsList = orderRepo.getOrderProductsList(orderId);
			return orderProductsList;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	

}
