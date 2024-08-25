package ar.com.uala.ms_publicacion.config;

import ar.com.uala.ms_publicacion.dto.UsuarioDto;
import ar.com.uala.ms_publicacion.repository.PublicacionRepository;
import ar.com.uala.ms_publicacion.repository.UsuarioRepository;
import ar.com.uala.ms_publicacion.service.PublicacionService;
import ar.com.uala.ms_publicacion.service.UsuarioService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpClientErrorException;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;

@Configuration
public class ServiceConfig {

    private static final Long ID_USUARIO_VALIDO = 1L;
    private static final Long ID_USUARIO_NO_VALIDO = 10L;
    private static final Long ID_USUARIO_SERVICIO_CAIDO = 404L;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Bean("test-usuario")
    public UsuarioService usuarioServiceConRepoMockeado() {
        UsuarioRepository usuarioRepositoryMock = Mockito.mock(UsuarioRepository.class);

        UsuarioDto usuarioObtenido = new UsuarioDto(ID_USUARIO_VALIDO, "ezortega", asList(2L));

        when(usuarioRepositoryMock.obtener(ID_USUARIO_VALIDO)).thenReturn(usuarioObtenido);
        when(usuarioRepositoryMock.obtener(ID_USUARIO_NO_VALIDO)).thenThrow(HttpClientErrorException.NotAcceptable.class);
        when(usuarioRepositoryMock.obtener(ID_USUARIO_SERVICIO_CAIDO)).thenThrow(HttpClientErrorException.NotFound.class);

        return new UsuarioService(usuarioRepositoryMock);
    }

    @Bean("test-publicacion")
    public PublicacionService publicacionServiceConRepoUsuarioMockeado() {
        return new PublicacionService(publicacionRepository, usuarioServiceConRepoMockeado());
    }
}
