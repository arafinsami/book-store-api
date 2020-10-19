package com.sami.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sami.entity.AppUser;
import com.sami.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserService {

	private final AppUserRepository repository;

	private final BCryptPasswordEncoder encoder;

	public AppUser save(AppUser user) {
		user.setPassword(encoder.encode(user.getPassword()));
		AppUser u = repository.save(user);
		return u;
	}
}