package ar.com.uala.ms_publicacion.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenido;
    private Long usuarioId;
    private LocalDateTime fechaCreacion;

    public Publicacion(Long usuarioId, String contenido) {
        this.usuarioId = usuarioId;
        this.contenido = contenido;
        this.fechaCreacion = LocalDateTime.now();
    }
}
