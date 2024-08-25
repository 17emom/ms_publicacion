package ar.com.uala.ms_publicacion.controller.rest;

import ar.com.uala.ms_publicacion.domain.Publicacion;
import ar.com.uala.ms_publicacion.dto.ContenidoDto;
import ar.com.uala.ms_publicacion.service.PublicacionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Page<Publicacion> obtenerTimeline(@PathVariable Long usuarioId, @PageableDefault(size = 5, sort = "fechaCreacion", direction = Sort.Direction.DESC) Pageable pageable) {
        return publicacionService
                .obtenerTimeline(usuarioId, pageable);
    }
}
