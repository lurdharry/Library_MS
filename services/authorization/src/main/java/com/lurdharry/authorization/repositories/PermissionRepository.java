package com.lurdharry.authorization.repositories;

import com.lurdharry.authorization.model.Permission;
import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    Optional<Permission> findByName(String name);
}
