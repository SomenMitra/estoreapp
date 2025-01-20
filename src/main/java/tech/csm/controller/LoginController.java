package tech.csm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import tech.csm.model.UserModel;
import tech.csm.service.UserService;
import tech.csm.util.JwtUtil;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
@Slf4j
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/hello")
	public ResponseEntity<String> appCheck() {
		return ResponseEntity.status(HttpStatus.OK).body("Hello app testing success in users");
	}

	@PostMapping("/signUp")
	public ResponseEntity<Map<String, Object>> signUp(@RequestBody UserModel user) {
		Map<String, Object> responseMap = new HashMap<>();
		try {
			Boolean userExist = userService.verifyUser(user);
			if (userExist) {
				responseMap.clear();
				responseMap.put("code", "found");
				responseMap.put("message", "Email already registered!");
				return ResponseEntity.ok(responseMap);
			}
			responseMap.clear();
			responseMap.put("message", userService.createUser(user));
			responseMap.put("code", "success");
			return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
		} catch (Exception e) {
			responseMap.clear();
			responseMap.put("error", "Server Error");
			responseMap.put("Exception", e.getMessage());
			log.error("Exception occurred in :: signUp :: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
		}
	}

	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody UserModel user) {
		Map<String, Object> responseMap = new HashMap<>();
		try {
			Boolean isAuthenticated = userService.verifyUser(user);
			if (!isAuthenticated) {
				responseMap.clear();
				responseMap.put("code", "unauthorized");
				responseMap.put("message", "Invalid credentials!");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
			}
			UserModel validatedUser = userService.findByEmailPassword(user);
			validatedUser.setPassword("xxxxxxxx");
			String token = jwtUtil.generateToken(validatedUser);
			responseMap.clear();
			responseMap.put("code", "success");
			responseMap.put("message", "Login successful!");
			responseMap.put("token", token); 
			return ResponseEntity.ok(responseMap);

		} catch (Exception e) {
			 log.error("Exception occurred in :: login :: ", e);
			responseMap.clear();
			responseMap.put("error", "Server Error");
			responseMap.put("Exception", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
		}
	}

}
