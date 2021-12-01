package cl.ucn.dge.salud.visadocertificados.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface SolicitudResumenAdministrador {
    Long getId();
    @JsonProperty("fecha_inicio_solicitud")
    LocalDateTime getFechaInicioSolicitud();
    @JsonProperty("nombre_alumnno")
    @Value("#{target.estudiante.nombre + ' ' + target.estudiante.primerApellido}")
    String getNombreAlumno();
    @Value("#{target.estudiante.correo}")
    String getCorreo();

}
