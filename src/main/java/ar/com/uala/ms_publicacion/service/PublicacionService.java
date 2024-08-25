package ar.com.uala.ms_publicacion.service;

import ar.com.uala.ms_publicacion.domain.Publicacion;
import ar.com.uala.ms_publicacion.repository.PublicacionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("prod-publicacion")
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final UsuarioService usuarioService;
    private static final int CANTIDAD_CARACTERES_LIMITE = 280;

    public PublicacionService(PublicacionRepository publicacionRepository, @Qualifier("prod-usuario") UsuarioService usuarioService) {
        this.publicacionRepository = publicacionRepository;
        this.usuarioService = usuarioService;
    }

    public Publicacion crear(Long usuarioId, String contenido) {
        validarUsuario(usuarioId);
        validarContenido(contenido);

        Publicacion publicacion = new Publicacion(usuarioId, contenido);

        return publicacionRepository.save(publicacion);
    }

    private void validarUsuario(Long id) {
        Assert.notNull(id, "El id del usuario no puede ser nulo");
        Assert.isTrue(usuarioService.existePorId(id), "El usuario no existe");
    }

    private void validarContenido(String contenido) {
        Assert.hasText(contenido, "El contenido de la publicacion no puede ser nulo ni estar vacio");
        Assert.isTrue(contenido.length() <= CANTIDAD_CARACTERES_LIMITE, "El contenido supera el limite de caracteres");
    }
}
