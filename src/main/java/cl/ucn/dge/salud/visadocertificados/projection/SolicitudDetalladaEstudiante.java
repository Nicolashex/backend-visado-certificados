package cl.ucn.dge.salud.visadocertificados.projection;

import cl.ucn.dge.salud.visadocertificados.model.Documento;
import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SolicitudDetalladaEstudiante {

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

    @JsonProperty("nombre_medico_tratante")
    String getNombreMedicoTratante();

    @JsonProperty("rut_medico_tratante")
    String getRutMedicoTratante(); //rut medico

    @JsonProperty("fecha_inicio_reposo")
    LocalDate getFechaInicioReposo();

    @JsonProperty("fecha_fin_reposo")
    LocalDate getFechaFinReposo();

    List<Documento> getDocumentos();

    String getComentario();

    Solicitud.estadosPosibles getEstado();

    @Value("#{target.estudiante.correo}")
    String getCorreo();

    @JsonProperty("es_carga")
    boolean isEsCarga();

    @JsonProperty("nombre_carga")
    String getNombreCarga();

    @JsonProperty("rut_carga")
    String getRutCarga();

    @JsonProperty("isValorado")
    boolean isValorado();

    @JsonProperty("id_certificado")
    @Value("#{(target.certificado!= null ? (target.certificado.id):null)}")
    String idCertificado();

}
