package cl.ucn.dge.salud.visado_certificados.repository;

import cl.ucn.dge.salud.visado_certificados.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioDocumento extends JpaRepository<Documento, Long> {

    @Query("SELECT d FROM Documento d WHERE d.id=?1")
    Documento getDocumentoById(Long id);


}
