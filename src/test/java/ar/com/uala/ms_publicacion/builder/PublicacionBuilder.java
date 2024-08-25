package ar.com.uala.ms_publicacion.builder;

import ar.com.uala.ms_publicacion.domain.Publicacion;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;

public class PublicacionBuilder {
    private Long id;
    private String contenido;
    private Long usuarioId;
    private LocalDateTime fechaCreacion;

    public static PublicacionBuilder crear() {
        return new PublicacionBuilder();
    }

    public PublicacionBuilder conId(Long id) {
        this.id = id;
        return this;
    }

    public PublicacionBuilder conContenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public PublicacionBuilder conUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
        return this;
    }

    public PublicacionBuilder conFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public PublicacionBuilder conFechaCreacionAhora() {
        this.fechaCreacion = LocalDateTime.now();
        return this;
    }

    public Publicacion build() {
        return new Publicacion(id, contenido, usuarioId, fechaCreacion);
    }

    public Publicacion buildAndPersist(EntityManager em) {
        Publicacion publicacion = this.build();
        em.persist(publicacion);
        em.flush();
        em.detach(publicacion);
        return publicacion;
    }
}
