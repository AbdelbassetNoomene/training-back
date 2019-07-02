package tn.cni.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.cni.training.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
