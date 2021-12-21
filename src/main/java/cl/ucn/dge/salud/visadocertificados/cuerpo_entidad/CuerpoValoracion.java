package cl.ucn.dge.salud.visadocertificados.cuerpo_entidad;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CuerpoValoracion {

    @JsonProperty("id_solicitud")
    @NotBlank(message = "Se requiere el id de la solicitud")
    private Long idSolicitud;

    @NotNull(message = "Se requiere una valoracion valida")
    @NotBlank(message = "Se requiere una valoracion")
    @Min(1)
    @Max(5)
    private Integer valoracion1;

    @NotNull(message = "Se requiere una valoracion valida")
    @NotBlank(message = "Se requiere una valoracion")
    @Min(1)
    @Max(5)
    private Integer valoracion2;

    @NotNull(message = "Se requiere una valoracion valida")
    @NotBlank(message = "Se requiere una valoracion")
    @Min(1)
    @Max(5)
    private Integer valoracion3;

    private String comentario;

    public CuerpoValoracion(Long idSolicitud, Integer valoracion1, Integer valoracion2, Integer valoracion3, String comentario) {
        this.idSolicitud = idSolicitud;
        this.valoracion1 = valoracion1;
        this.valoracion2 = valoracion2;
        this.valoracion3 = valoracion3;
        this.comentario = comentario;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getValoracion1() {
        return valoracion1;
    }

    public void setValoracion1(Integer valoracion1) {
        this.valoracion1 = valoracion1;
    }

    public Integer getValoracion2() {
        return valoracion2;
    }

    public void setValoracion2(Integer valoracion2) {
        this.valoracion2 = valoracion2;
    }

    public Integer getValoracion3() {
        return valoracion3;
    }

    public void setValoracion3(Integer valoracion3) {
        this.valoracion3 = valoracion3;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
