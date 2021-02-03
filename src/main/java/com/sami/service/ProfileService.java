package com.sami.service;

import static com.sami.constants.Constants.PROFILE_UPDATE_COMMENTS;
import static com.sami.enums.Action.UPDATE;
import static com.sami.enums.ModuleName.PROFILE_UPDATE;
import static java.lang.String.valueOf;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sami.entity.AppUser;
import com.sami.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

	private final AppUserRepository repository;

	private final ActionLogService actionLogService;

	private final BCryptPasswordEncoder encoder;

	public AppUser update(AppUser user) {

		user.setPassword(encoder.encode(user.getPassword()));
		AppUser u = repository.save(user);

		actionLogService.publishActivity(
				PROFILE_UPDATE,
				UPDATE,
				valueOf(u.getId()),
				PROFILE_UPDATE_COMMENTS
		);
		return u;
	}

	public Optional<AppUser> get(Long id) {
		return repository.findById(id);
	}
}
