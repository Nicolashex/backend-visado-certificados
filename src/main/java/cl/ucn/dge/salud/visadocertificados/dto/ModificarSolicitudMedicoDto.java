package cl.ucn.dge.salud.visadocertificados.dto;

import cl.ucn.dge.salud.visadocertificados.model.Solicitud;

import javax.validation.constraints.NotBlank;

public class ModificarSolicitudMedicoDto {

    @NotBlank(message = "Debe seleccionar un estado")
    Solicitud.estadosPosibles estado;

    String comentario;

    public ModificarSolicitudMedicoDto() {
    }

    public ModificarSolicitudMedicoDto(Solicitud.estadosPosibles estado, String comentario) {
        this.estado = estado;
        this.comentario = comentario;
    }

    public Solicitud.estadosPosibles getEstado() {
        return estado;
    }

    public void setEstado(Solicitud.estadosPosibles estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
