package com.sachet.userservice.repo;

import com.sachet.userservice.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Boolean existsRolesByUserRole(String userRole);

    List<Roles> getRolesByUserRole(String userRole);
}
