package tn.cni.training.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import tn.cni.training.dto.UserDTO;
import tn.cni.training.model.Role;
import tn.cni.training.model.User;
import tn.cni.training.service.UserService;
import tn.cni.training.utilities.ConvertUtilities;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public CustomAuthenticationProvider() {
		super();
	}

	// API

	@Override
	public Authentication authenticate(final Authentication authentication) {
		try {
			String login = (String) authentication.getPrincipal();
			String password = (String) authentication.getCredentials();

			LOGGER.info("Authentification for user: {}", login + " & password : {}" + password);

			User user;
			User userEntity = new User();

			if (!login.contains("@")) throw new RuntimeException("email must contain @");
			user = userService.findUserByMail(login);
			if (user == null) {
				LOGGER.info("USERNAME_NOT_FOUND");
				throw new RuntimeException("USERNAME_NOT_FOUND");
			}

			LOGGER.info("user: " + user.toString());
			if (passwordEncoder.matches(password, user.getPassword()) || password.equals(user.getPassword())) {
				userEntity = user;
			}

			UserDTO authentificationResponseDTO = new UserDTO();
			List<Role> autoritiesList = new ArrayList<>(userEntity.getRoles());
			authentificationResponseDTO = ConvertUtilities.toUserDTO(user);
			authentificationResponseDTO.setRoles(new ArrayList<String>());
			authentificationResponseDTO.setAuthorities(new ArrayList<GrantedAuthority>());
			for (Role authEntity : autoritiesList) {
				authentificationResponseDTO.getRoles().add(authEntity.getRoleName().trim());
				authentificationResponseDTO.getAuthorities()
						.add(new SimpleGrantedAuthority(authEntity.getRoleName().trim()));
			}
			return new UsernamePasswordAuthenticationToken(authentificationResponseDTO, null,
					authentificationResponseDTO.getAuthorities());
		} finally {

		}
	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}