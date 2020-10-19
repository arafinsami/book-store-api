package com.sami.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sami.entity.AppUser;
import com.sami.entity.Permission;
import com.sami.entity.Role;
import com.sami.repository.AppUserRepository;
import com.sami.repository.PermissionRepository;
import com.sami.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "setup")
@RequiredArgsConstructor
public class SetupController {

	private final AppUserRepository userRepository;
	
	private final PermissionRepository permissionRepository;
	
	private final RoleRepository roleRepository;
	
	private final PasswordEncoder passwordEncoder;

	@GetMapping
	@ResponseBody
	public ResponseEntity<?> setup() {

		List<Permission> permissions = permissionRepository.findAll();
		
		Set<Permission> pSets = permissions.stream().collect(Collectors.toSet());
		
		List<Role> roles = new ArrayList<>();

		Role role = new Role();
		role.setName("ROLE_ADMIN");
		role.setTitle("Admin");
		role.setPermissions(pSets);
		roleRepository.save(role);
		
		roles.add(role);
		
		Set<Role> sRoles = roles.stream().collect(Collectors.toSet());
		
		AppUser user = new AppUser();
		user.setFirstName("CPTU ADMIN");
		user.setLastName("CPTU ADMIN");
		user.setUsername("admin");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setRoles(sRoles);
		user.setEnabled(true);
		user.setLastPasswordResetDate(Calendar.getInstance().getTime());
		userRepository.save(user);

		return ResponseEntity.ok("DONE");
	}
}


