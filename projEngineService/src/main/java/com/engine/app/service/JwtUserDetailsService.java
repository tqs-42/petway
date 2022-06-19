package com.engine.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.engine.app.config.JwtTokenUtil;
import com.engine.app.exception.InvalidCredentialsException;
import com.engine.app.model.JwtRequest;
import com.engine.app.model.JwtResponse;
import com.engine.app.model.Person;
import com.engine.app.repository.PersonRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
    private PersonRepository personRepository;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Person person = personRepository.findByEmail(email).orElseThrow(() -> {
            return new UsernameNotFoundException("Person does not exist");
        });

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(person.getClass().getSimpleName()));

        return new User(person.getEmail(), person.getPassword(), authorities);
	}

    public void authenticate(JwtRequest jwtRequest) throws InvalidCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }
    }

    public JwtResponse generateTokenLogin(JwtRequest jwtRequest) throws InvalidCredentialsException, UsernameNotFoundException {
        this.authenticate(jwtRequest);
		final UserDetails userDetails = this.loadUserByUsername(jwtRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
        return new JwtResponse(token, userDetails.getAuthorities().iterator().next(), userDetails.getUsername());
    }


}