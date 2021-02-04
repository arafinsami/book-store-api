package com.sami.service;

import static com.sami.constants.Comments.SIGNUP_COMMENT;
import static com.sami.enums.Action.SAVE;
import static com.sami.enums.ModuleName.SIGNUP;
import static java.lang.String.valueOf;

import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sami.entity.AppUser;
import com.sami.entity.Role;
import com.sami.repository.AppUserRepository;
import com.sami.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserService {

	private final AppUserRepository repository;

	private final RoleRepository roleRepository;

	private final BCryptPasswordEncoder encoder;

	private final ActionLogService actionLogService;

	public AppUser save(AppUser user) {
		user.setPassword(encoder.encode(user.getPassword()));
		AppUser u = repository.save(user);
		return u;
	}

	public AppUser signup(AppUser appUser) {

		Set<Role> roles = roleRepository.findByName("ROLE_USER");
		appUser.setPassword(encoder.encode(appUser.getPassword()));
		appUser.setRoles(roles);
		appUser.setEnabled(true);
		appUser.setLastPasswordResetDate(Calendar.getInstance().getTime());
		AppUser user = repository.save(appUser);
		
		actionLogService.publishActivity(
				SIGNUP,
				SAVE,
				valueOf(user.getId()),
				SIGNUP_COMMENT
		);
		return user;
	}

	public AppUser findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public Optional<AppUser> findByEmail(String email) {
		return repository.findByEmail(email);
	}
}


