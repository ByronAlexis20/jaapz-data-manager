package com.jaap.datamanager.springSecurity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaap.datamanager.seguridad.models.dao.IUsuarioDAO;
import com.jaap.datamanager.seguridad.models.entity.Usuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	IUsuarioDAO usuarioDAO;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
			Usuario user = usuarioDAO.findByUsuario(usuario)
					.orElseThrow(() -> new UsernameNotFoundException("Indetificación no encontrada; identificación: " + usuario));
			return UserDetailsImpl.build(user);
	}
}