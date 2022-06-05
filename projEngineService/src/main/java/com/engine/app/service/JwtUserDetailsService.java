package com.engine.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.directory.InvalidAttributeValueException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.engine.app.exception.PersonNotFoundException;
import com.engine.app.model.Person;
import com.engine.app.repository.PersonRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
    private PersonRepository personRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Person person = personRepository.findByEmail(email).orElseThrow(() -> {
            return new UsernameNotFoundException("Person does not exist");
        });

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(person.getClass().getSimpleName()));
		System.out.println("ROLE" + person.getClass().getSimpleName());

        return new User(person.getEmail(), person.getPassword(), authorities);
	}

}
