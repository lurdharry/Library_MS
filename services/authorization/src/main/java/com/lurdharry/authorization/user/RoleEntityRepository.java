package com.lurdharry.authorization.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleEntityRepository extends JpaRepository<RoleEntity,Integer> {

    Optional<RoleEntity> findByName(Role name);
}
