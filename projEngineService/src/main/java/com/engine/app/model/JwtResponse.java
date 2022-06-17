package com.engine.app.model;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final GrantedAuthority role;
	private final String email;

	public JwtResponse(String jwttoken, GrantedAuthority role, String email) {
		this.jwttoken = jwttoken;
		this.role = role;
		this.email = email;
	}

	public String getToken() {
		return this.jwttoken;
	}

	public GrantedAuthority getRole() {
        return this.role;
    }

	public String getEmail() {
        return this.email;
    }


}