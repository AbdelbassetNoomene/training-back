package tn.cni.training.service;

import java.io.IOException;

import tn.cni.training.dto.UserDTO;
import tn.cni.training.model.Role;
import tn.cni.training.model.User;

public interface UserService {
	
	UserDTO saveUser(UserDTO user) throws IOException ;

	UserDTO updateUser(UserDTO user) throws IOException ;

	UserDTO findById(long id);

	void deleteUser(long id);

	Role saveRole(Role role);

	void addRoleToUser(String mail, String roleName);

	User findUserByMail(String login);

}
