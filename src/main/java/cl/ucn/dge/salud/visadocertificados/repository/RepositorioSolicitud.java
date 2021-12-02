package cl.ucn.dge.salud.visadocertificados.repository;

import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import cl.ucn.dge.salud.visadocertificados.model.User;
import cl.ucn.dge.salud.visadocertificados.projection.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositorioSolicitud extends JpaRepository<Solicitud, Long> {

    @Query("SELECT s FROM Solicitud s WHERE s.id=?1")
    Solicitud getSolicitudById(Long id);

    @Query("SELECT COUNT(s)  FROM Solicitud s WHERE s.estudiante=?1 and s.estado=?2")
    long countByEstudianteAndStatus(User user, String status);

    List<SolicitudResumenAdministrador> getSolicitudByEstado(Solicitud.estadosPosibles estado);

    @Query("SELECT s FROM Solicitud s WHERE s.id=?1")
    SolicitudDetalladaAdministrador getSolicitudDetalladaAdministrador(Long id);

    @Query("SELECT s FROM Solicitud s WHERE s.id=?1 ")
    SolicitudDetalladaMedico getSolicitudDetalladaMedico(Long id);

    @Query("SELECT s FROM Solicitud s WHERE s.id=?1")
    SolicitudDetalladaEstudiante getSolicitudDetalladaEstudiante(Long id);

    @Query("SELECT s FROM Solicitud s WHERE s.estudiante.id=?1")
    List<SolicitudResumenEstudiante> getSolicitudesEstudiante(Long id);

    @Query("SELECT s FROM Solicitud s WHERE s.idProfesional.id=?1 and s.estado =?2")
    List<SolicitudResumenMedico> getSolicitudesPorMedicoYEstado(Long id, Solicitud.estadosPosibles estado);

    boolean existsSolicitudByEstudianteAndId(User estudiante,Long id);

    boolean existsSolicitudByIdProfesionalAndId(User idProfesional, Long id);
}
