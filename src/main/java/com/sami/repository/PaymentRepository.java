package com.sami.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sami.entity.AppUser;
import com.sami.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	List<Payment> findByAppUser(AppUser appUser);
}
