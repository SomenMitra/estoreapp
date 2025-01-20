package tech.csm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.csm.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
