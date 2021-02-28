package com.sami.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sami.entity.AppUser;
import com.sami.entity.Shipping;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {

	List<Shipping> findByAppUser(AppUser appUser);
}
