package com.sami.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sami.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

	AppUser findByUsername (String username);
	
	AppUser findByEmail(String email);

}
