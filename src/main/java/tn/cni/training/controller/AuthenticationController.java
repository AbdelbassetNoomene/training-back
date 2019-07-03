package tn.cni.training.controller;

import java.io.IOException;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import tn.cni.training.dto.AuthenticationRequest;
import tn.cni.training.dto.TokenResponse;
import tn.cni.training.dto.UserDTO;
import tn.cni.training.security.TokenUtils;

@RestController
@RequestMapping("auth")
@Slf4j
public class AuthenticationController {



	@Value("${token.header}")
	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtils tokenUtils;

	private final ObjectMapper jsonMapper = new ObjectMapper();
	

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TokenResponse> authenticationRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)throws Exception {

		String tokenResponse="";
		 String header = httpServletRequest.getHeader("Authorization");
	        log.info("Request Header credentials: {}", header);

	        if ((header == null) || (!header.startsWith("Basic "))) {
	            throw new AuthenticationException();
	        }
	        AuthenticationRequest authenticationRequestDTO = new AuthenticationRequest();
	       

	        try {
	            String[] tokens = tokenUtils.extractAndDecodeToken(header);
	            if (tokens.length != 2) {
	            	throw new IOException("FAIL_DECODE_TOKEN");
	            }
	            
	            String login    = tokens[0];
	            String password = tokens[1];
	            authenticationRequestDTO.setUsername(login);
	            authenticationRequestDTO.setPassword(password);
	            String jsonUserDetails = null;

	            UsernamePasswordAuthenticationToken vUsernamePasswordAuth = new UsernamePasswordAuthenticationToken(login, authenticationRequestDTO.getPassword());
	            Authentication authentication = authenticationManager.authenticate(vUsernamePasswordAuth);
	            UserDTO userDTO = (UserDTO) authentication.getPrincipal();

	            if ((authentication.isAuthenticated()) && (authentication.getPrincipal() != null)
	                    && ((authentication.getPrincipal() instanceof UserDTO))) {

	                jsonUserDetails = jsonMapper.writeValueAsString(authentication.getPrincipal());
	                log.info("Authenticated user: {}", jsonUserDetails);
	            }
	            tokenResponse=tokenUtils.generateToken(userDTO);
	           
	        } catch (IOException e) {
	        	throw new Exception("FAIL_DECODE_TOKEN");
	        }
	        TokenResponse t=new TokenResponse();
            t.setToken(tokenResponse);
	        return new ResponseEntity<TokenResponse>(t, HttpStatus.OK);

	}
	}
