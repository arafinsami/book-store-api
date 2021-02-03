package com.sami.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sami.entity.Billing;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

}
