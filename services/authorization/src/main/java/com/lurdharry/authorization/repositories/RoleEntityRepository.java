package com.lurdharry.authorization.repositories;

import com.lurdharry.authorization.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleEntityRepository extends JpaRepository<RoleEntity,Integer> {

    Optional<RoleEntity> findByName(String name);
}
