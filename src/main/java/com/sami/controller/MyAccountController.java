package com.sami.controller;

import static com.sami.utils.ApiError.error;
import static com.sami.utils.ApiResponseBuilder.errors;
import static com.sami.utils.ApiResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sami.dto.LoginDto;
import com.sami.dto.SignupDto;
import com.sami.entity.AppUser;
import com.sami.entity.Permission;
import com.sami.entity.Role;
import com.sami.repository.AppUserRepository;
import com.sami.security.ActiveUserContext;
import com.sami.security.TokenProvider;
import com.sami.service.AppUserService;
import com.sami.validator.MyAccountValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "my-account")
@RequiredArgsConstructor
@Api(tags = "my account's Data")
public class MyAccountController {

	private final AuthenticationManager authenticationManager;

	private final TokenProvider tokenProvider;

	private final UserDetailsService userDetailsService;

	private final AppUserRepository appUserRepository;

	private final AppUserService appUserService;

	private final MyAccountValidator myAccountValidator;
	
	private final ActiveUserContext activeUserContext;

	@PostMapping("/login")
	@ApiOperation(value = "user login", response = LoginDto.class)
	public ResponseEntity<JSONObject> authenticationToken(@RequestBody LoginDto login) throws AuthenticationException {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = userDetailsService.loadUserByUsername(login.getUsername());

		String accessToken = tokenProvider.generateToken(userDetails);

		String refreshToken = tokenProvider.generateRefreshToken(userDetails);

		AppUser appUser = appUserRepository.findByUsername(login.getUsername());
		
		String username = activeUserContext.getLoggedInUserName();

		List<Permission> permissions = new ArrayList<Permission>();
		Set<Role> roles = appUser.getRoles();

		roles.forEach(r -> {
			permissions.addAll(r.getPermissions());
		});

		Map<String, Object> token = new HashMap<String, Object>();
		token.put("token", accessToken);
		token.put("refreshToken", refreshToken);
		token.put("username", username);
		token.put("permissions", permissions.stream().map(Permission::getRouteName).collect(Collectors.toList()));

		return ok(success(token).getJson());
	}

	@PostMapping("/signup")
	@ApiOperation(value = "user signup", response = SignupDto.class)
	public ResponseEntity<JSONObject> signup(@Valid @RequestBody SignupDto dto, BindingResult bindingResult) {

		ValidationUtils.invokeValidator(myAccountValidator, dto, bindingResult);

		if (bindingResult.hasErrors()) {
			return ok(errors(error(bindingResult)).getJson());
		}

		AppUser user = dto.to();

		appUserService.signup(user);
		return ok(success(SignupDto.from(user)).getJson());
	}
	
}
