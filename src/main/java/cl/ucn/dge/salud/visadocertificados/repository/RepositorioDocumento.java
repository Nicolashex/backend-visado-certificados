package cl.ucn.dge.salud.visadocertificados.repository;

import cl.ucn.dge.salud.visadocertificados.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioDocumento extends JpaRepository<Documento, String> {

    Documento getDocumentoById(String Id);

    boolean existsById(String id);

}
