package ar.com.uala.ms_publicacion.service;

import ar.com.uala.ms_publicacion.exception.ExternalServiceUnavailableException;
import ar.com.uala.ms_publicacion.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service("prod-usuario")
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public boolean existePorId(Long id) {
        validarDatoEntrada(id);
        try {
            usuarioRepository.obtener(id);
            return true;
        } catch (HttpClientErrorException.NotAcceptable e) {
            return false;
        } catch (Exception e) {
            throw new ExternalServiceUnavailableException("Error al consultar el ms de usuario", e);
        }
    }

    public List<Long> obtenerSeguidos(Long id) {
        validarDatoEntrada(id);
        try {
            return usuarioRepository
                    .obtener(id)
                    .getSeguidos();
        } catch (HttpClientErrorException.NotAcceptable e) {
            throw new IllegalArgumentException("El usuario no existe", e);
        } catch (Exception e) {
            throw new ExternalServiceUnavailableException("Error al consultar el ms de usuario", e);
        }
    }

    private void validarDatoEntrada(Long id) {
        Assert.notNull(id, "El usuario no puede ser nulo");
    }
}
