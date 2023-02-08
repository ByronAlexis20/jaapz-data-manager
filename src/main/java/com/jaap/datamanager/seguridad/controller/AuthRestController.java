package com.jaap.datamanager.seguridad.controller;

import java.net.UnknownHostException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaap.datamanager.seguridad.models.entity.Permiso;
import com.jaap.datamanager.seguridad.models.entity.Usuario;
import com.jaap.datamanager.seguridad.service.IPermisoService;
import com.jaap.datamanager.seguridad.service.IUsuarioService;
import com.jaap.datamanager.springSecurity.entity.JwtRespuesta;
import com.jaap.datamanager.springSecurity.entity.LoginSolicitud;
import com.jaap.datamanager.springSecurity.jwt.JwtUtils;
import com.jaap.datamanager.springSecurity.services.UserDetailsImpl;
import com.jaap.datamanager.util.Constantes;

@CrossOrigin(origins = { Constantes.urlServidorFront }, maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

	@Autowired 
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPermisoService permisoService;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUserFuncionario(@Valid @RequestBody LoginSolicitud LoginSolicitud) throws UnknownHostException {
		String clave_final = LoginSolicitud.getPassword();
		encoder.encode(clave_final);
		System.out.println("entra al login");
		System.out.println(LoginSolicitud.getUser());
		System.out.println(LoginSolicitud.getPassword());
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(LoginSolicitud.getUser(), clave_final));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		Usuario usu = this.usuarioService.buscarPorIdUsuario(userDetails.getCodUsuario());
		
		List<Permiso> listaPermiso = this.permisoService.buscarPorIdPerfil(usu.getPerfil().getId());
		
		return ResponseEntity.ok(new JwtRespuesta( 	jwt, 
													usu.getId(), 
													usu.getCedula(),
													usu.getNombres(), 
													usu.getApellidos(),
													usu.getPerfil(),
													usu.getUsuario(),
													listaPermiso
													));
	}
	
	@GetMapping(value = "/test")
	public ResponseEntity<?> test() {
		return new ResponseEntity<String>("No autorizado", HttpStatus.OK);
	}
	
}
