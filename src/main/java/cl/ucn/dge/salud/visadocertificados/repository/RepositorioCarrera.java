package cl.ucn.dge.salud.visadocertificados.repository;

import cl.ucn.dge.salud.visadocertificados.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioCarrera  extends JpaRepository<Carrera, Long> {


    @Query("SELECT c FROM Carrera c WHERE c.id = ?1")
    Carrera getCarreraById(Long id);






}
