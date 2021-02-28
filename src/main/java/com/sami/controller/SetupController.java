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

import com.sami.dto.BookDto;
import com.sami.entity.AppUser;
import com.sami.entity.Permission;
import com.sami.entity.Role;
import com.sami.repository.AppUserRepository;
import com.sami.repository.PermissionRepository;
import com.sami.repository.RoleRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "setup")
@RequiredArgsConstructor
@Api(tags = "APP Setup")
public class SetupController {

	private final AppUserRepository userRepository;
	
	private final PermissionRepository permissionRepository;
	
	private final RoleRepository roleRepository;
	
	private final PasswordEncoder passwordEncoder;

	@GetMapping
	@ResponseBody
	@ApiOperation(value = "app setup", response = BookDto.class)
	public ResponseEntity<?> setup() {

		List<Permission> permissions = permissionRepository.findAll();
		
		Set<Permission> pSets = permissions.stream().collect(Collectors.toSet());
		
		List<Role> roles = new ArrayList<>();

		Role role = roleRepository.findByName("ROLE_ADMIN");
		role.setPermissions(pSets);
		roleRepository.save(role);
		roles.add(role);
		
		Set<Role> roleSet = roles.stream().collect(Collectors.toSet());
		
		AppUser user = new AppUser();
		user.setFirstName("ADMIN");
		user.setLastName("ADMIN");
		user.setUsername("admin");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setRoles(roleSet);
		user.setEnabled(true);
		user.setLastPasswordResetDate(Calendar.getInstance().getTime());
		userRepository.save(user);

		return ResponseEntity.ok("DONE");
	}
}


