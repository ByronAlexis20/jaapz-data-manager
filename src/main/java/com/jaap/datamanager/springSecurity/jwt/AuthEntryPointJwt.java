package com.jaap.datamanager.springSecurity.jwt;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error("Unauthorized error: {}", authException.getMessage());
		String nom_error = "";
		if(authException.getMessage().equals("Bad credentials")) {
			nom_error = "Malas Credenciales";
		}else if(authException.getMessage().equals("Full authentication is required to access this resource")){
			nom_error = "Se requiere autenticación completa para acceder a este recurso";
		}else{
			nom_error = authException.getMessage();
		}
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Autorizado - ".concat(nom_error));
	}

}