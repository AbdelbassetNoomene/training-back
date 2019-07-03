package tn.cni.training.utilities;

import java.util.ArrayList;
import java.util.List;

import tn.cni.training.dto.UserDTO;
import tn.cni.training.model.User;

public class ConvertUtilities {

	public static UserDTO toUserDTO(User u) {
		UserDTO user = new UserDTO();
		user.setEmail(u.getEmail());
		user.setFirstname(u.getFirstname());
		user.setId(u.getId());
		user.setLastname(u.getLastname());
		user.setPhone(u.getPhone());
		user.setImage(u.getImage());
		List<String> roles = new ArrayList<>();
		u.getRoles().stream().forEach(r -> roles.add(r.getRoleName()));
		user.setRoles(roles);
		return user;
	}

	public static User toUser(UserDTO u) {
		User user = new User();
		user.setEmail(u.getEmail());
		user.setFirstname(u.getFirstname());
		user.setId(u.getId());
		user.setLastname(u.getLastname());
		user.setPhone(u.getPhone());
		user.setImage(u.getImage());
		return user;
	}

}
