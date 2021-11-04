package cl.ucn.dge.salud.visado_certificados.repository;

import cl.ucn.dge.salud.visado_certificados.model.Solicitud;
import cl.ucn.dge.salud.visado_certificados.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorioSolicitud extends JpaRepository<Solicitud, Long> {

    @Query("SELECT s FROM Solicitud s WHERE s.id=?1")
    Solicitud getSolicitudById(Long id);

    @Query("SELECT COUNT(s)  FROM Solicitud s WHERE s.estudiante=?1 and s.estado=?2")
    long countByEstudianteAndStatus(User user, String status);
}
