package cl.ucn.dge.salud.visadocertificados.dto;

import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.RequestBody;

public class ModificarSolicitudAdministradorDto {

    @JsonProperty("id")
    private Long idProfesional;
    String comentario;
    Solicitud.estadosPosibles estado;

    public ModificarSolicitudAdministradorDto(Long idProfesional, String comentario, Solicitud.estadosPosibles estado) {
        this.idProfesional = idProfesional;
        this.comentario = comentario;
        this.estado = estado;
    }

    public Long getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(Long idProfesional) {
        this.idProfesional = idProfesional;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Solicitud.estadosPosibles getEstado() {
        return estado;
    }

    public void setEstado(Solicitud.estadosPosibles estado) {
        this.estado = estado;
    }
}

