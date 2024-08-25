package ar.com.uala.ms_publicacion.repository;

import ar.com.uala.ms_publicacion.domain.Publicacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    Page<Publicacion> findAllByUsuarioIdIn(List<Long> seguidosIds, Pageable pageable);

}
