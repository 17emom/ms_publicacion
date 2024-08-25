package ar.com.uala.ms_publicacion.service;

import ar.com.uala.ms_publicacion.exception.ExternalServiceUnavailableException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    @Qualifier("test-usuario")
    private UsuarioService usuarioService;

    //Repositorio mockeado
    private static final Long USUARIO_VALIDO = 1L;
    private static final Long USUARIO_NO_VALIDO = 10L;
    private static final Long USUARIO_SERVICIO_CAIDO = 404L;

    //Metodo existePorId

    @Test
    public void existePorId_conIdNulo_lanzaExcepcion() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> usuarioService.existePorId(null))
                .withMessage("El usuario no puede ser nulo");
    }

    @Test
    public void existePorId_conServicioCaido_lanzaExcepcion() {
        assertThatExceptionOfType(ExternalServiceUnavailableException.class)
                .isThrownBy(() -> usuarioService.existePorId(USUARIO_SERVICIO_CAIDO))
                .withMessage("Error al consultar el ms de usuario");
    }

    @Test
    public void existePorId_conUsuarioNoValido_retornaFalse() {

        boolean existe = usuarioService.existePorId(USUARIO_NO_VALIDO);

        assertThat(existe).isFalse();
    }

    @Test
    public void existePorId_conUsuarioValido_retornaTrue() {
        boolean existe = usuarioService.existePorId(USUARIO_VALIDO);

        assertThat(existe).isTrue();
    }

    //Metodo obtenerSeguidos

    @Test
    public void obtenerSeguidos_conIdNulo_lanzaExcepcion() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> usuarioService.obtenerSeguidos(null))
                .withMessage("El usuario no puede ser nulo");
    }

    @Test
    public void obtenerSeguidos_conUsuarioNoExistente_lanzaExcepcion() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> usuarioService.obtenerSeguidos(USUARIO_NO_VALIDO))
                .withMessage("El usuario no existe");
    }

    @Test
    public void obtenerSeguidos_conServicioUsuarioCaido_lanzaExcepcion() {
        assertThatExceptionOfType(ExternalServiceUnavailableException.class)
                .isThrownBy(() -> usuarioService.obtenerSeguidos(USUARIO_SERVICIO_CAIDO))
                .withMessage("Error al consultar el ms de usuario");
    }

    @Test
    public void obtenerSeguidos_conUsuarioValido_retornaListaSeguidos() {
        Long seguidoId = 2L;

        List<Long> seguidosObtenidos = usuarioService.obtenerSeguidos(USUARIO_VALIDO);

        assertThat(seguidosObtenidos)
                .isNotEmpty()
                .containsExactly(seguidoId);
    }


}
