package tn.cni.training.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tn.cni.training.dto.UserDTO;
import tn.cni.training.model.Role;
import tn.cni.training.model.User;
import tn.cni.training.repository.RoleRepository;
import tn.cni.training.repository.UserRepository;
import tn.cni.training.utilities.ConvertUtilities;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;

	@Override
	public UserDTO saveUser(UserDTO user) throws IOException {
		if (user.getRoles().isEmpty())
			throw new RuntimeException("role vide");
		User user1 = userRepository.findByEmail(user.getEmail());
		if (user1 != null)
			throw new RuntimeException("Email already exists");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		User user2 = new User();
		user2 = ConvertUtilities.toUser(user);
		user2.setPassword(hashedPassword);
		user2.getRoles().add(roleRepository.findByRoleName("USER"));
		user2 = userRepository.save(user2);
		UserDTO userDto = new UserDTO();
		userDto = ConvertUtilities.toUserDTO(user2);
		return userDto;
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String mail, String roleName) {
		Role role = roleRepository.findByRoleName(roleName);
		User user = userRepository.findByEmail(mail);
		user.getRoles().add(role);
	}

	@Override
	public User findUserByMail(String login) {
		return userRepository.findByEmail(login);
	}

	@Override
	public UserDTO updateUser(UserDTO user) throws IOException {
		User user1 = userRepository.findByEmail(user.getEmail());
		if (user1 == null)
			throw new RuntimeException("User not existe");
		User user2 = new User();
		user1 = ConvertUtilities.toUser(user);
		user1.getRoles().add(roleRepository.findByRoleName("USER"));
		user2 = userRepository.save(user1);
		return ConvertUtilities.toUserDTO(user2);
	}

	@Override
	public void deleteUser(long id) {
		User user = userRepository.findById(id).get();
		if (user == null)
			throw new RuntimeException("User not existe");
		this.userRepository.delete(user);
	}

	@Override
	public UserDTO findById(long id) {
		User user = userRepository.findById(id).get();
		if (user == null)
			throw new RuntimeException("User not existe");
		UserDTO userDto = ConvertUtilities.toUserDTO(user);
		return userDto;
	}


}
