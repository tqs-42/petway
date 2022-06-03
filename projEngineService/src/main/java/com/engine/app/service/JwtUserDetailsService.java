package com.engine.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.engine.app.model.Rider;
import com.engine.app.repository.RiderRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
    private RiderRepository riderRepository;

	@Override
	public UserDetails loadUserByUsername(String email)  {

		Rider rider = riderRepository.findByEmail(email);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(rider.getClass().getSimpleName()));

        return new User(rider.getEmail(), rider.getPassword(), authorities);
	}

}
