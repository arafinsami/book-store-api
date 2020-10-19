package com.sami.controller;

import static com.sami.utils.ApiResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sami.dto.LoginDto;
import com.sami.entity.AppUser;
import com.sami.entity.Permission;
import com.sami.entity.Role;
import com.sami.repository.AppUserRepository;
import com.sami.security.TokenProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "login")
@RequiredArgsConstructor
public class LoginController {

	private final AuthenticationManager authenticationManager;

	private final TokenProvider tokenProvider;

	private final UserDetailsService userDetailsService;

	private final AppUserRepository appUserRepository;

	@PostMapping
	public ResponseEntity<JSONObject> authenticationToken(@RequestBody LoginDto login) throws AuthenticationException {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(login.getUsername());
		
		String accessToken = tokenProvider.generateToken(userDetails);
		
		String refreshToken = tokenProvider.generateRefreshToken(userDetails);
		
		AppUser appUser = appUserRepository.findByUsername(login.getUsername());

		List<Permission> permissions = new ArrayList<Permission>();
		Set<Role> roles = appUser.getRoles();

		roles.forEach(r -> {
			permissions.addAll(r.getPermissions());
		});

		Map<String, Object> token = new HashMap<String, Object>();
		token.put("token", accessToken);
		token.put("refreshToken", refreshToken);
		token.put("username", userDetails.getUsername());
		token.put("permissions", permissions.stream().map(Permission::getRouteName).collect(Collectors.toList()));
		
		return ok(success(token).getJson());
	}
}
