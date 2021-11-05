package cl.ucn.dge.salud.visadocertificados.repository;

import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import cl.ucn.dge.salud.visadocertificados.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioSolicitud extends JpaRepository<Solicitud, Long> {

    @Query("SELECT s FROM Solicitud s WHERE s.id=?1")
    Solicitud getSolicitudById(Long id);

    @Query("SELECT COUNT(s)  FROM Solicitud s WHERE s.estudiante=?1 and s.estado=?2")
    long countByEstudianteAndStatus(User user, String status);
}
