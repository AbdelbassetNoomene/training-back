package tn.cni.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.cni.training.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByRoleName(String roleName);

}
