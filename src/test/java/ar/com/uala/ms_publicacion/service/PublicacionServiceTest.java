package ar.com.uala.ms_publicacion.service;

import ar.com.uala.ms_publicacion.domain.Publicacion;
import ar.com.uala.ms_publicacion.exception.ExternalServiceUnavailableException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PublicacionServiceTest {

    @Autowired
    @Qualifier("test-publicacion")
    private PublicacionService publicacionService;

    private static final Long USUARIO_CREADO_ID = 1L;

    //Metodo crear

    @Test
    public void crear_conIdUsuarioNulo_lanzaExcepcion() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> {publicacionService.crear(null, "test");})
                .withMessage("El id del usuario no puede ser nulo");
    }

    @Test
    public void crear_conUsuarioInexistente_lanzaExcepcion() {
        Long usuarioNoCreadoId = 10L;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> {publicacionService.crear(usuarioNoCreadoId, "test");})
                .withMessage("El usuario no existe");
    }

    @Test
    public void crear_conServicioUsuarioCaido_lanzaExcepcion() {
        Long usuarioServicioCaiodoId = 404L;

        assertThatExceptionOfType(ExternalServiceUnavailableException.class)
                .isThrownBy(() -> {publicacionService.crear(usuarioServicioCaiodoId, "test");})
                .withMessage("Error al consultar el ms de usuario");
    }

    @Test
    public void crear_conUsuarioValidoYContenidoNulo_lanzaExcepcion() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> {publicacionService.crear(USUARIO_CREADO_ID, null);})
                .withMessage("El contenido de la publicacion no puede ser nulo ni estar vacio");
    }

    @Test
    public void crear_conUsuarioValidoYContenidoVacio_lanzaExcepcion() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> {publicacionService.crear(USUARIO_CREADO_ID, "");})
                .withMessage("El contenido de la publicacion no puede ser nulo ni estar vacio");
    }

    @Test
    public void crear_conUsuarioValidoYContenidoSuperandoLimiteCaracteres_lanzaExcepcion() {
        String contenidoSuperandoLimiteCaracteres = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> {publicacionService.crear(USUARIO_CREADO_ID, contenidoSuperandoLimiteCaracteres);})
                .withMessage("El contenido supera el limite de caracteres");
    }

    @Test
    public void crear_conUsuarioYContenidoValido_creaPublicacion() {
        String contenidoValido = "test";

        LocalDateTime antes = LocalDateTime.now();
        Publicacion publicacionCreada = publicacionService.crear(USUARIO_CREADO_ID, contenidoValido);
        LocalDateTime despues = LocalDateTime.now();

        assertThat(publicacionCreada)
                .usingRecursiveComparison()
                .comparingOnlyFields("usuarioId", "contenido");
        assertThat(publicacionCreada.getId())
                .isNotNull();
        assertThat(publicacionCreada.getFechaCreacion())
                .isNotNull()
                .isBetween(antes, despues);
    }
}
