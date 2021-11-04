package cl.ucn.dge.salud.visado_certificados.repository;

import cl.ucn.dge.salud.visado_certificados.model.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositorioCarrera  extends JpaRepository<Carrera, Long> {


    @Query("SELECT c FROM Carrera c WHERE c.id = ?1")
    Carrera getCarreraById(Long id);






}
