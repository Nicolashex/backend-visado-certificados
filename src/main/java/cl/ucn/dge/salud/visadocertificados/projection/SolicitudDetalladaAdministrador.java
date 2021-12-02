package cl.ucn.dge.salud.visadocertificados.projection;

import cl.ucn.dge.salud.visadocertificados.model.Documento;
import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SolicitudDetalladaAdministrador {
    Long getId();
    @JsonProperty("fecha_inicio_solicitud")
    LocalDateTime getFechaInicioSolicitud();
    @JsonProperty("nombre_alumnno")
    @Value("#{target.estudiante.nombre + ' ' + target.estudiante.primerApellido+ ' '+ target.estudiante.segundoApellido}")
    String getNombreAlumno();
    @Value("#{target.estudiante.rut}")
    String getRut();
    @Value("#{target.estudiante.carrera.nombre}")
    String getCarrera();

    String getMotivo();

    String getNombreMedicoTratante();

    String getIdProfesional(); //rut medico

    LocalDate getFechaInicioReposo();

    LocalDate getFechaFinReposo();

    List<Documento> getDocumentos();

    String getComentario();

    Solicitud.estadosPosibles getEstado();

    @Value("#{target.IdProfesional.nombre + ' ' + target.IdProfesional.apellido}")
    String getProfesional();

    @Value("#{target.estudiante.correo}")
    String getCorreo();
}
