package tech.csm.service;

import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.csm.model.UserModel;
import tech.csm.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public Boolean verifyUser(UserModel user) {
		Integer count = userRepo.verifyUser(user.getEmail());
		return count > 0 ? true : false;
	}

	@Override
	@Transactional
	public String createUser(UserModel user) {
		UserModel savedUser = userRepo.saveAndFlush(user);
		return "User created with ID: " + savedUser.getUserId();
	}
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel user = userRepo.findByEmail(username)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .authorities(Collections.emptyList()) 
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

	@Override
	public UserModel findByEmailPassword(UserModel user) {
		UserModel userFound = userRepo.findByEmailPassword(user.getEmail(), user.getPassword())
				.orElseThrow(() -> new UsernameNotFoundException(
						"User not found with username: " + user.getEmail() + " and provided password"));

		return userFound;
	}

	@Override
	public UserModel getUserByEmail(String email) {
		Integer count = userRepo.verifyUser(email);
		UserModel user = null;
		if (count > 0) {
			user = userRepo.findByEmail(email).orElse(null);
		}
		return user;
	}

}
