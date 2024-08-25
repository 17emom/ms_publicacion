package ar.com.uala.ms_publicacion.service;

import ar.com.uala.ms_publicacion.exception.ExternalServiceUnavailableException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    @Qualifier("test-usuario")
    private UsuarioService usuarioService;

    @Test
    public void existePorId_conIdNulo_lanzaExcepcion() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> usuarioService.existePorId(null))
                .withMessage("El usuario no existe");
    }

    @Test
    public void existePorId_conServicioCaido_lanzaExcepcion() {
        Long usuarioServicioCaido = 404L;
        assertThatExceptionOfType(ExternalServiceUnavailableException.class)
                .isThrownBy(() -> usuarioService.existePorId(usuarioServicioCaido))
                .withMessage("Error al consultar el ms de usuario");
    }

    @Test
    public void existePorId_conUsuarioNoValido_retornaFalse() {
        Long usuarioNoValidoId = 10L;

        boolean existe = usuarioService.existePorId(usuarioNoValidoId);

        assertThat(existe).isFalse();
    }

    @Test
    public void existePorId_conUsuarioValido_retornaTrue() {
        Long usuarioValidoId = 1L;

        boolean existe = usuarioService.existePorId(usuarioValidoId);

        assertThat(existe).isTrue();
    }
}
