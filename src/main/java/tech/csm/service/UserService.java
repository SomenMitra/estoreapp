package tech.csm.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import tech.csm.model.UserModel;

public interface UserService {

	Boolean verifyUser(UserModel user);

	String createUser(UserModel user);

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	UserModel findByEmailPassword(UserModel user);

	UserModel getUserByEmail(String email);

}
