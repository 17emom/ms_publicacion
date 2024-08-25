package ar.com.uala.ms_publicacion.service;

import ar.com.uala.ms_publicacion.builder.PublicacionBuilder;
import ar.com.uala.ms_publicacion.domain.Publicacion;
import ar.com.uala.ms_publicacion.exception.ExternalServiceUnavailableException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class PublicacionServiceTest {

    @Autowired
    @Qualifier("test-publicacion")
    private PublicacionService publicacionService;

    @Autowired
    private EntityManager em;

    private static final Long USUARIO_CREADO_ID = 1L;
    private static final Long USUARIO_NO_CREADO_ID = 10L;
    private static final Pageable PAGEABLE = PageRequest.of(0, 5, Sort.Direction.DESC, "fechaCreacion");

    //Metodo crear

    @Test
    public void crear_conIdUsuarioNulo_lanzaExcepcion() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> {
                    publicacionService.crear(null, "test");
                })
                .withMessage("El id del usuario no puede ser nulo");
    }

    @Test
    public void crear_conUsuarioInexistente_lanzaExcepcion() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> {
                    publicacionService.crear(USUARIO_NO_CREADO_ID, "test");
                })
                .withMessage("El usuario no existe");
    }

    @Test
    public void crear_conServicioUsuarioCaido_lanzaExcepcion() {
        Long usuarioServicioCaiodoId = 404L;

        assertThatExceptionOfType(ExternalServiceUnavailableException.class)
                .isThrownBy(() -> {
                    publicacionService.crear(usuarioServicioCaiodoId, "test");
                })
                .withMessage("Error al consultar el ms de usuario");
    }

    @Test
    public void crear_conUsuarioValidoYContenidoNulo_lanzaExcepcion() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> {
                    publicacionService.crear(USUARIO_CREADO_ID, null);
                })
                .withMessage("El contenido de la publicacion no puede ser nulo ni estar vacio");
    }

    @Test
    public void crear_conUsuarioValidoYContenidoVacio_lanzaExcepcion() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> {
                    publicacionService.crear(USUARIO_CREADO_ID, "");
                })
                .withMessage("El contenido de la publicacion no puede ser nulo ni estar vacio");
    }

    @Test
    public void crear_conUsuarioValidoYContenidoSuperandoLimiteCaracteres_lanzaExcepcion() {
        String contenidoSuperandoLimiteCaracteres = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> {
                    publicacionService.crear(USUARIO_CREADO_ID, contenidoSuperandoLimiteCaracteres);
                })
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
                .comparingOnlyFields("usuarioId", "contenido")
                .isEqualTo(new Publicacion(USUARIO_CREADO_ID, contenidoValido));
        assertThat(publicacionCreada.getId())
                .isNotNull();
        assertThat(publicacionCreada.getFechaCreacion())
                .isNotNull()
                .isBetween(antes, despues);
    }

    //Metodo obtenerTimeline

    @Test
    public void obtenerTimeline_conIdUsuarioNulo_lanzaExcepcion() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> publicacionService.obtenerTimeline(null, PAGEABLE))
                .withMessage("El id del usuario no puede ser nulo");
    }

    @Test
    public void obtenerTimeline_conUsuarioInexistente_lanzaExcepcion() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> publicacionService.obtenerTimeline(USUARIO_NO_CREADO_ID, PAGEABLE))
                .withMessage("El usuario no existe");
    }

    @Test
    public void obtenerTimeline_conPageableNulo_lanzaExcepcion() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> publicacionService.obtenerTimeline(USUARIO_CREADO_ID, null))
                .withMessage("El paginado no puede ser nulo");
    }

    @Test
    public void obtenerTimeline_conUsuarioYPageableValidosPeroSinPublicaciones_retornaPaginadoConContenidoVacio() {
        Page<Publicacion> paginado = publicacionService.obtenerTimeline(USUARIO_CREADO_ID, PAGEABLE);

        assertThat(paginado.getTotalElements())
                .isZero();
    }

    @Test
    public void obtenerTimeline_conUsuarioYPageableValidosConPublicaciones_retornaPaginadoConContenido() {
        Publicacion publicacion = PublicacionBuilder.crear().conUsuarioId(2L).conContenido("test").conFechaCreacionAhora().buildAndPersist(em);

        Page<Publicacion> paginado = publicacionService.obtenerTimeline(USUARIO_CREADO_ID, PAGEABLE);

        assertThat(paginado.getTotalElements())
                .isNotZero();
        assertThat(paginado.getContent())
                .isNotEmpty()
                .hasSize(1)
                .allSatisfy(
                        (elemento) -> assertThat(elemento)
                                .usingRecursiveComparison()
                                .comparingOnlyFields("usuarioId", "contenido", "fechaCreacion")
                                .isEqualTo(publicacion)
                );
    }
}
