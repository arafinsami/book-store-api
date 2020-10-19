package com.sami.security;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sami.entity.AppUser;
import com.sami.repository.AppUserRepository;

@Service
public class AuthUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AppUserRepository appUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		AppUser user = appUserRepository.findByUsername(username);
		if (Objects.isNull(user)) {
			return null;
		}
		return AuthUserFactory.create(user);
	}

}

