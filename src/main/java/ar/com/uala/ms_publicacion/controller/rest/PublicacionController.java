package ar.com.uala.ms_publicacion.controller.rest;

import ar.com.uala.ms_publicacion.domain.Publicacion;
import ar.com.uala.ms_publicacion.service.PublicacionService;
import ar.com.uala.ms_publicacion.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/v1/publicacion/{usuarioId}")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;
    @Autowired
    @Qualifier("prod")
    private UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public boolean crear(@PathVariable Long usuarioId, @RequestBody String textoPublicacion) {
        return usuarioService.existePorId(usuarioId);
    }

    @GetMapping("/timeline")
    @ResponseStatus(HttpStatus.OK)
    public List<Publicacion> obtenerTimeline(@PathVariable Long usuarioId, Pageable pageable) {
        return null;
    }
}
