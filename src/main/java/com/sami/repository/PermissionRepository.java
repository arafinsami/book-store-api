package com.sami.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sami.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
