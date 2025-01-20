package tech.csm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tech.csm.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
	
	@Query(
		    value = "SELECT * FROM products WHERE category_id = :categoryId",
		    nativeQuery = true
	)
	List<Product> findAllBySubcategoryId(@Param("categoryId") Integer subCategoryId);

	@Query(
		    value = "SELECT p.* " +
		            "FROM products p " +
		            "JOIN categories c ON p.category_id = c.id " +
		            "WHERE c.parent_category_id = :mainCategoryId",
		    nativeQuery = true
		)
		List<Product> findAllByMainCategoryId(@Param("mainCategoryId") Integer mainCategoryId);

	@Query(
		    value = "SELECT p.* " +
		            "FROM products p " +
		            "WHERE p.category_id = :subCategoryId " +
		            "AND p.keywords LIKE %:keyword%",
		    nativeQuery = true
		)
		List<Product> getAllProductsBySubcategoryIdAndKeyword(@Param("subCategoryId") Integer subCategoryId, @Param("keyword") String keyword);


	@Query(
		    value = "SELECT p.* " +
		            "FROM products p " +
		            "JOIN categories c ON p.category_id = c.id " +
		            "WHERE c.parent_category_id = :mainCategoryId " +
		            "AND p.keywords LIKE %:keyword%",
		    nativeQuery = true
		)
		List<Product> getAllProductsByMainCategoryIdAndKeyword(@Param("mainCategoryId") Integer mainCategoryId, @Param("keyword") String keyword);




	

}
