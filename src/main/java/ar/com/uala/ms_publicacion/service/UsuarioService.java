package ar.com.uala.ms_publicacion.service;

import ar.com.uala.ms_publicacion.exception.ExternalServiceUnavailableException;
import ar.com.uala.ms_publicacion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;

@Service("prod-usuario")
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public boolean existePorId(Long id) {
        Assert.notNull(id, "El usuario no existe");
        try {
            usuarioRepository.obtener(id);
            return true;
        } catch (HttpClientErrorException.NotAcceptable e) {
            return false;
        } catch (Exception e) {
            throw new ExternalServiceUnavailableException("Error al consultar el ms de usuario", e);
        }
    }
}
