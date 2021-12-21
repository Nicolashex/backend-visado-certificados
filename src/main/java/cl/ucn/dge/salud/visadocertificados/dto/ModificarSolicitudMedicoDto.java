package cl.ucn.dge.salud.visadocertificados.dto;

import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class ModificarSolicitudMedicoDto {

    @NotBlank(message = "Debe seleccionar un estado")
    Solicitud.estadosPosibles estado;

    @JsonProperty("comentario_medico")
    String comentarioMedico;

    public ModificarSolicitudMedicoDto() {
    }

    public ModificarSolicitudMedicoDto(Solicitud.estadosPosibles estado, String comentarioMedico) {
        this.estado = estado;
        this.comentarioMedico = comentarioMedico;
    }

    public Solicitud.estadosPosibles getEstado() {
        return estado;
    }

    public void setEstado(Solicitud.estadosPosibles estado) {
        this.estado = estado;
    }

    public String getComentarioMedico() {
        return comentarioMedico;
    }

    public void setComentarioMedico(String comentarioMedico) {
        this.comentarioMedico = comentarioMedico;
    }
}
