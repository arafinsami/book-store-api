package com.sami.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.sami.entity.AppUser;
import com.sami.entity.Permission;
import com.sami.entity.Role;


public final class AuthUserFactory {

	private AuthUserFactory() {
	}

	public static AuthUser create(AppUser user) {
		return new AuthUser(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(),
				user.getPassword(), getAuthorities(user.getRoles()), user.getEnabled(),
				user.getLastPasswordResetDate());
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
		return getGrantedAuthorities(getPrivileges(roles));
	}

	private static List<String> getPrivileges(Collection<Role> roles) {

		List<String> privileges = new ArrayList<>();
		List<Permission> collection = new ArrayList<>();
		roles.forEach(role -> {
			collection.addAll(role.getPermissions());
		});
		collection.forEach(item -> {
			privileges.add(item.getAuthority());
		});
		return privileges;
	}

	private static List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		privileges.forEach(privilege -> {
			authorities.add(new SimpleGrantedAuthority(privilege));
		});
		return authorities;
	}

}

