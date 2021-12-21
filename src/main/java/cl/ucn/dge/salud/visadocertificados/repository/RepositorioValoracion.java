package cl.ucn.dge.salud.visadocertificados.repository;


import cl.ucn.dge.salud.visadocertificados.model.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioValoracion extends JpaRepository<Valoracion,Long> {

}
