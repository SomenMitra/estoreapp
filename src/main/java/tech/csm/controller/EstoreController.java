package tech.csm.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import tech.csm.model.Category;
import tech.csm.model.Product;
import tech.csm.service.CategoryService;
import tech.csm.service.ProductService;
import tech.csm.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/estore")
@Slf4j
public class EstoreController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@GetMapping("/hello")
	public ResponseEntity<String> appCheck() {
		return ResponseEntity.status(HttpStatus.OK).body("Hello app testing success");
	}

	@GetMapping("/getCategories")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categoryList;
		try {
			categoryList = categoryService.getAllCategories();
			return ResponseEntity.status(HttpStatus.OK).body(categoryList);
		} catch (Exception e) {
			 log.error("Exception occurred in :: getAllCategories :: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@GetMapping("/getAllProducts")
	public ResponseEntity<List<Product>> getAllProducts(
			@RequestParam(value = "subcategoryid", required = false) Integer subCategoryId,
			@RequestParam(value = "maincategoryid", required = false) Integer mainCategoryId,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<Product> productList;
		try {
			if (subCategoryId != null) {
				if (keyword != null) {
					productList = productService.getAllProductsBySubcategoryIdAndKeyword(subCategoryId,keyword);
					return ResponseEntity.status(HttpStatus.OK).body(productList);
				}
				productList = productService.getAllProductsBySubcategoryId(subCategoryId);
				return ResponseEntity.status(HttpStatus.OK).body(productList);
			} else if (mainCategoryId != null) {
				if (keyword != null) {
					productList = productService.getAllProductsByMainCategoryIdAndKeyword(mainCategoryId,keyword);
					return ResponseEntity.status(HttpStatus.OK).body(productList);
				}
				productList = productService.getAllProductsByMainCategoryId(mainCategoryId);
				return ResponseEntity.status(HttpStatus.OK).body(productList);
			}
			productList = productService.getAllProducts();
			return ResponseEntity.status(HttpStatus.OK).body(productList);
		} catch (Exception e) {
			 log.error("Exception occurred in :: getAllProducts :: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/getProductById/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") Integer productId) {
		try {
			Optional<Product> optionalProduct = productService.getProductById(productId);
			if (optionalProduct.isPresent())
				return ResponseEntity.status(HttpStatus.OK).body(optionalProduct.get());
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

		} catch (Exception e) {
			 log.error("Exception occurred in :: getProductById :: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	

}
