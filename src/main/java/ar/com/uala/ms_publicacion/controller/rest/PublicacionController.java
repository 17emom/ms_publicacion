package ar.com.uala.ms_publicacion.controller.rest;

import ar.com.uala.ms_publicacion.domain.Publicacion;
import ar.com.uala.ms_publicacion.dto.ContenidoDto;
import ar.com.uala.ms_publicacion.service.PublicacionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/v1/publicacion/{usuarioId}")
public class PublicacionController {

    private final PublicacionService publicacionService;

    public PublicacionController(@Qualifier("prod-publicacion") PublicacionService publicacionService) {
        this.publicacionService = publicacionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Publicacion crear(@PathVariable Long usuarioId, @RequestBody ContenidoDto contenidoDto) {
        return publicacionService.crear(usuarioId, contenidoDto.getContenido());
    }

    @GetMapping("/timeline")
    @ResponseStatus(HttpStatus.OK)
    public List<Publicacion> obtenerTimeline(@PathVariable Long usuarioId, Pageable pageable) {
        return null;
    }
}
