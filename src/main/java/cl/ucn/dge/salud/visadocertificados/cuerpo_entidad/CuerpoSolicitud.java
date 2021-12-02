package cl.ucn.dge.salud.visadocertificados.cuerpo_entidad;

import cl.ucn.dge.salud.visadocertificados.validacion.ValidacionRut;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public class CuerpoSolicitud {

    @NotBlank(message = "Se requiere el nombre del medico")
    @JsonProperty("nombre_medico_tratante")
    private String nombreMedicoTratante;
    @NotNull(message = "Fecha no puede estar en blanco")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("fecha_inicio_reposo")
    private LocalDate fechaInicioReposo;
    @NotNull(message = "Fecha no puede estar en blanco")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("fecha_fin_reposo")
    private LocalDate fechaFinReposo;
    @NotBlank(message = "se requiere ingresar un motivo")
    private String motivo;
    @NotNull(message = "se requiere ingresar si esta asociado a una carga o no")
    @JsonProperty("es_carga")
    private boolean esCarga;
    @ValidacionRut(message = "Se requiere un rut valido para la carga")
    @JsonProperty("rut_carga")
    private String rutCarga;
    @Pattern(message = "Nombre de carga no valido",
            regexp = "^[a-zA-Z]{4,}(?: [a-zA-Z]+)?(?: [a-zA-Z]+)?(?: [a-zA-Z]+)?$")
    @JsonProperty("nombre_carga")
    private String nombreCarga;

    @JsonProperty("rut_medico_tratante")
    private String rutMedicoTratante;



    public CuerpoSolicitud(String rutMedicoTratante, String nombreMedicoTratante, LocalDate fechaInicioReposo,
                           LocalDate fechaFinReposo, String motivo, boolean esCarga,
                           String rutCarga, String nombreCarga) {
        this.rutMedicoTratante = rutMedicoTratante;
        this.nombreMedicoTratante = nombreMedicoTratante;
        this.fechaInicioReposo = fechaInicioReposo;
        this.fechaFinReposo = fechaFinReposo;
        this.motivo = motivo;
        this.esCarga = esCarga;
        this.rutCarga = rutCarga;
        this.nombreCarga = nombreCarga;
    }

    public String getNombreMedicoTratante() {
        return nombreMedicoTratante;
    }

    public void setNombreMedicoTratante(String nombreMedicoTratante) {
        this.nombreMedicoTratante = nombreMedicoTratante;
    }

    public LocalDate getFechaInicioReposo() {
        return fechaInicioReposo;
    }

    public void setFechaInicioReposo(LocalDate fechaInicioReposo) {
        this.fechaInicioReposo = fechaInicioReposo;
    }

    public LocalDate getFechaFinReposo() {
        return fechaFinReposo;
    }

    public void setFechaFinReposo(LocalDate fechaFinReposo) {
        this.fechaFinReposo = fechaFinReposo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean esCarga() {
        return esCarga;
    }

    public void setEsCarga(boolean esCarga) {
        this.esCarga = esCarga;
    }

    public String getRutCarga() {
        return rutCarga;
    }

    public void setRutCarga(String rutCarga) {
        this.rutCarga = rutCarga;
    }

    public String getNombreCarga() {
        return nombreCarga;
    }

    public void setNombreCarga(String nombreCarga) {
        this.nombreCarga = nombreCarga;
    }

    public boolean isEsCarga() {
        return esCarga;
    }

    public String getRutMedicoTratante() {
        return rutMedicoTratante;
    }

    public void setRutMedicoTratante(String rutMedicoTratante) {
        this.rutMedicoTratante = rutMedicoTratante;
    }

    @Override
    public String toString() {
        return "CuerpoSolicitud{" +
                "nombreMedicoTratante='" + nombreMedicoTratante + '\'' +
                ", fechaInicioReposo=" + fechaInicioReposo +
                ", fechaFinReposo=" + fechaFinReposo +
                ", motivo='" + motivo + '\'' +
                ", esCarga=" + esCarga +
                ", rutCarga='" + rutCarga + '\'' +
                ", nombreCarga='" + nombreCarga + '\'' +
                ", rutMedicoTratante='" + rutMedicoTratante + '\'' +
                '}';
    }
}
