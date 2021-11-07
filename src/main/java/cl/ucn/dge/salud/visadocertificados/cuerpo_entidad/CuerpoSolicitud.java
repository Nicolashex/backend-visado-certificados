package cl.ucn.dge.salud.visadocertificados.cuerpo_entidad;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class CuerpoSolicitud {

    private Long idAlumno;
    private String nombrePaciente;
    private String rutPaciente;
    private String carrera;
    private String nombreMedicoTratante;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicioReposo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFinReposo;
    private String motivo;

    public CuerpoSolicitud(Long idAlumno, String nombrePaciente, String rutPaciente,
                           String carrera, String nombreMedicoTratante,
                           LocalDate fechaInicioReposo, LocalDate fechaFinReposo,
                           String motivo) {
        this.idAlumno = idAlumno;
        this.nombrePaciente = nombrePaciente;
        this.rutPaciente = rutPaciente;
        this.carrera = carrera;
        this.nombreMedicoTratante = nombreMedicoTratante;
        this.fechaInicioReposo = fechaInicioReposo;
        this.fechaFinReposo = fechaFinReposo;
        this.motivo = motivo;
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

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    @Override
    public String toString() {
        return "CuerpoSolicitud{" +
                "idAlumno=" + idAlumno +
                ", nombrePaciente='" + nombrePaciente + '\'' +
                ", rutPaciente='" + rutPaciente + '\'' +
                ", carrera='" + carrera + '\'' +
                ", nombreMedicoTratante='" + nombreMedicoTratante + '\'' +
                ", fechaInicioReposo=" + fechaInicioReposo +
                ", fechaFinReposo=" + fechaFinReposo +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}
