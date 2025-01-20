package tech.csm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.csm.model.Product;
import tech.csm.repo.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepo productRepo;

	@Override
	public List<Product> getAllProducts() {
		
		return productRepo.findAll();
	}

	@Override
	public Optional<Product> getProductById(Integer productId) {	
		return productRepo.findById(productId);
	}

	@Override
	public List<Product> getAllProductsBySubcategoryId(Integer subCategoryId) {
		
		return productRepo.findAllBySubcategoryId(subCategoryId);
	}

	@Override
	public List<Product> getAllProductsByMainCategoryId(Integer mainCategoryId) {
		return productRepo.findAllByMainCategoryId(mainCategoryId);
	}

	@Override
	public List<Product> getAllProductsBySubcategoryIdAndKeyword(Integer subCategoryId, String keyword) {
		
		return productRepo.getAllProductsBySubcategoryIdAndKeyword(subCategoryId,keyword);
	}

	@Override
	public List<Product> getAllProductsByMainCategoryIdAndKeyword(Integer mainCategoryId, String keyword) {
		
		return productRepo.getAllProductsByMainCategoryIdAndKeyword(mainCategoryId,keyword);
	}

	
	

}
