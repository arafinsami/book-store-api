package com.sami.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sami.entity.Shipping;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {

}
