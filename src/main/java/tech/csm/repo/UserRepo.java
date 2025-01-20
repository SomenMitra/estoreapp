package tech.csm.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.csm.model.UserModel;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Integer> {

	@Query(value = "SELECT COUNT(*) FROM users WHERE email = :email", nativeQuery = true)
	Integer verifyUser(@Param("email") String email);

	@Query(value = "SELECT * FROM users WHERE email = :email",nativeQuery = true)
	Optional<UserModel> findByEmail(@Param("email") String username);

	@Query(value = "SELECT * FROM users WHERE email = :email and password =:pass", nativeQuery = true)
	Optional<UserModel> findByEmailPassword(@Param("email") String email,@Param("pass") String password);

	

}
