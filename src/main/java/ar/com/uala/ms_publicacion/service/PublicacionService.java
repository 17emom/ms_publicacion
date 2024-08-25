package ar.com.uala.ms_publicacion.service;

import ar.com.uala.ms_publicacion.repository.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicacionService {
    @Autowired
    private PublicacionRepository publicacionRepository;
    @Autowired
    private UsuarioService usuarioService;
}
