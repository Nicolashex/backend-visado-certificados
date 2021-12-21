package cl.ucn.dge.salud.visadocertificados.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RespuestaValidacionDto {

    @JsonProperty("es_valido")
    private boolean esValido;

    private String mensaje;

    public RespuestaValidacionDto(boolean esValido, String mensaje) {
        this.esValido = esValido;
        this.mensaje = mensaje;
    }

    public boolean isEsValido() {
        return esValido;
    }

    public void setEsValido(boolean esValido) {
        this.esValido = esValido;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
