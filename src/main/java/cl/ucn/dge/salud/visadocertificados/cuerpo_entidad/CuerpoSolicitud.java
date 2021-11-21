package cl.ucn.dge.salud.visadocertificados.cuerpo_entidad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public class CuerpoSolicitud {

    @NotBlank(message = "Se requiere un nombre")
    @Pattern(message = "Nombre no valido",
            regexp = "^(?=.{1,40}$)[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$")
    @JsonProperty("nombre_paciente")
    private String nombrePaciente;
    @NotBlank(message = "Se requiere un rut del paciente")
    @JsonProperty("rut_paciente")
    private String rutPaciente;
    @NotBlank(message = "Se requiere una carrera")
    private String carrera;
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
    @JsonProperty("rut_carga")
    private String rutCarga;
    @JsonProperty("nombre_carga")
    private String nombreCarga;


    public CuerpoSolicitud(String nombrePaciente, String rutPaciente,
                           String carrera, String nombreMedicoTratante,
                           LocalDate fechaInicioReposo, LocalDate fechaFinReposo,
                           String motivo, boolean esCarga, String rutCarga, String nombreCarga) {
        this.nombrePaciente = nombrePaciente;
        this.rutPaciente = rutPaciente;
        this.carrera = carrera;
        this.nombreMedicoTratante = nombreMedicoTratante;
        this.fechaInicioReposo = fechaInicioReposo;
        this.fechaFinReposo = fechaFinReposo;
        this.motivo = motivo;
        this.esCarga = esCarga;
        this.rutCarga = rutCarga;
        this.nombreCarga = nombreCarga;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getRutPaciente() {
        return rutPaciente;
    }

    public void setRutPaciente(String rutPaciente) {
        this.rutPaciente = rutPaciente;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
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

    @Override
    public String toString() {
        return "CuerpoSolicitud{" +
                "nombrePaciente='" + nombrePaciente + '\'' +
                ", rutPaciente='" + rutPaciente + '\'' +
                ", carrera='" + carrera + '\'' +
                ", nombreMedicoTratante='" + nombreMedicoTratante + '\'' +
                ", fechaInicioReposo=" + fechaInicioReposo +
                ", fechaFinReposo=" + fechaFinReposo +
                ", motivo='" + motivo + '\'' +
                ", esCarga=" + esCarga +
                ", rutCarga='" + rutCarga + '\'' +
                ", nombreCarga='" + nombreCarga + '\'' +
                '}';
    }
}
