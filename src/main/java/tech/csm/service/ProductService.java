package tech.csm.service;

import java.util.List;
import java.util.Optional;

import tech.csm.model.Product;

public interface ProductService {

	List<Product> getAllProducts();

	Optional<Product> getProductById(Integer productId);

	List<Product> getAllProductsBySubcategoryId(Integer subCategoryId);

	List<Product> getAllProductsByMainCategoryId(Integer mainCategoryId);

	List<Product> getAllProductsBySubcategoryIdAndKeyword(Integer subCategoryId, String keyword);

	List<Product> getAllProductsByMainCategoryIdAndKeyword(Integer mainCategoryId, String keyword);

	

}
