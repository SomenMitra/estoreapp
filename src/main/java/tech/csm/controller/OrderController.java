package tech.csm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import tech.csm.dto.OrdersDto;
import tech.csm.model.UserModel;
import tech.csm.service.OrderService;
import tech.csm.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
@Slf4j
public class OrderController {

	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;

	@PostMapping("/add")
	public ResponseEntity<List<Map<String, Object>>> placeOrder(@RequestBody OrdersDto order) {
		try {
			UserModel user = userService.getUserByEmail(order.getUserEmail());
			List<Map<String, Object>> respMap = orderService.placeOrder(user, order);

			return ResponseEntity.ok(respMap);
		} catch (Exception e) {
			 log.error("Exception occurred in :: placeOrder :: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@GetMapping("/allOrders")
	public ResponseEntity<List<Map<String, Object>>> getAllOders(@RequestParam("userEmail") String userEmail) {
		try {
			UserModel user = userService.getUserByEmail(userEmail);
			List<Map<String, Object>> allOrderList = orderService.getAllOrderList(user.getUserId());
			return ResponseEntity.ok(allOrderList);
		} catch (Exception e) {
			 log.error("Exception occurred in :: getAllOders :: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@GetMapping("/orderProducts")
	public ResponseEntity<List<Map<String, Object>>> getOrderProducts(@RequestParam("orderId") Integer orderId) {
		try {
			List<Map<String, Object>> orderProductsList = orderService.getOrderProductsList(orderId);
			return ResponseEntity.ok(orderProductsList);
		} catch (Exception e) {
			 log.error("Exception occurred in :: getOrderProducts :: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
