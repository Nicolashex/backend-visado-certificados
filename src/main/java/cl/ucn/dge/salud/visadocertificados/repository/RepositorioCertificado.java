package cl.ucn.dge.salud.visadocertificados.repository;

import cl.ucn.dge.salud.visadocertificados.model.Certificado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCertificado extends JpaRepository<Certificado, String> {
}
