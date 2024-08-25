package ar.com.uala.ms_publicacion.repository;

import ar.com.uala.ms_publicacion.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository("prod")
public class UsuarioRepository {

    @Value("${ar.com.uala.ms_usuario.url}")
    private String urlBase;

    @Autowired
    private RestTemplate restTemplate;

    public UsuarioDto obtener(Long id) {
        String url = UriComponentsBuilder
                .fromHttpUrl(urlBase)
                .pathSegment(id.toString())
                .toUriString();

        return restTemplate.getForObject(url, UsuarioDto.class);
    }
}
