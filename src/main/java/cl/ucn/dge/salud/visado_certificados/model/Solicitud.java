package cl.ucn.dge.salud.visado_certificados.model;

import jdk.jfr.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "solicitudes")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false,unique = true)
    private Long id;

    @Column(name="idProfesional",nullable = true)
    private Long idProfesional;

    @Column(name="nombrePaciente",nullable = false)
    private String nombrePaciente;

    @Column(name = "rutPaciente",nullable = false,length = 15)
    private String rutPaciente;

    @Column(name="carrera",nullable = false)
    private String carrera;

    @Column(name="nombreMedicoTratante",nullable = false)
    private String nombreMedicoTratante;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name="fechaInicioReposo",nullable = false)
    private LocalDate fechaInicioReposo;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name="fechaFinReposo",nullable = false)
    private LocalDate fechaFinReposo;

    @Column(name="motivo",nullable = false,length = 255)
    private String motivo;

    @Column(name="rutaCertificadoMedico",nullable = false)
    private String rutaCertificadoMedico;

    @Column(name="estado",columnDefinition = "varchar(255) default 'Ingresada'" )
    private String estado;

    @Column(name="resolucion",columnDefinition = "varchar(255) default 'Por evaluar'" )
    private String resolucion;

    @Column(name="comentario")
    private String comentario;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @CreationTimestamp
    @Column(name="fechaInicioSolicitud",nullable = false)
    private LocalDateTime fechaInicioSolicitud;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name="fechaFinSolicitud")
    private LocalDateTime fechaFinSolicitud;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name="IngresoEvaluacion")
    private LocalDateTime IngresoEvaluacion;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name="RespuestaEvaluacion")
    private LocalDateTime RespuestaEvaluacion;

    @OneToMany(targetEntity = Documento.class, cascade=CascadeType.ALL)
    @JoinColumn(name = "solicitud",referencedColumnName = "id")
    private List<Documento> documentos;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User estudiante;

    public Solicitud() {
    }

    public Solicitud(Long idProfesional, String nombrePaciente, String rutPaciente, String carrera, String nombreMedicoTratante, LocalDate fechaInicioReposo, LocalDate fechaFinReposo, String motivo, String rutaCertificadoMedico, String estado, String resolucion, String comentario, LocalDateTime fechaInicioSolicitud, LocalDateTime fechaFinSolicitud, LocalDateTime ingresoEvaluacion, LocalDateTime respuestaEvaluacion, List<Documento> documentos, User estudiante) {
        this.idProfesional = idProfesional;
        this.nombrePaciente = nombrePaciente;
        this.rutPaciente = rutPaciente;
        this.carrera = carrera;
        this.nombreMedicoTratante = nombreMedicoTratante;
        this.fechaInicioReposo = fechaInicioReposo;
        this.fechaFinReposo = fechaFinReposo;
        this.motivo = motivo;
        this.rutaCertificadoMedico = rutaCertificadoMedico;
        this.estado = estado;
        this.resolucion = resolucion;
        this.comentario = comentario;
        this.fechaInicioSolicitud = fechaInicioSolicitud;
        this.fechaFinSolicitud = fechaFinSolicitud;
        IngresoEvaluacion = ingresoEvaluacion;
        RespuestaEvaluacion = respuestaEvaluacion;
        this.documentos = documentos;
        this.estudiante = estudiante;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public User getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(User estudiante) {
        this.estudiante = estudiante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(Long idProfesional) {
        this.idProfesional = idProfesional;
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

    public String getRutaCertificadoMedico() {
        return rutaCertificadoMedico;
    }

    public void setRutaCertificadoMedico(String rutaCertificadoMedico) {
        this.rutaCertificadoMedico = rutaCertificadoMedico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFechaInicioSolicitud() {
        return fechaInicioSolicitud;
    }

    public void setFechaInicioSolicitud(LocalDateTime fechaInicioSolicitud) {
        this.fechaInicioSolicitud = fechaInicioSolicitud;
    }

    public LocalDateTime getFechaFinSolicitud() {
        return fechaFinSolicitud;
    }

    public void setFechaFinSolicitud(LocalDateTime fechaFinSolicitud) {
        this.fechaFinSolicitud = fechaFinSolicitud;
    }

    public LocalDateTime getIngresoEvaluacion() {
        return IngresoEvaluacion;
    }

    public void setIngresoEvaluacion(LocalDateTime ingresoEvaluacion) {
        IngresoEvaluacion = ingresoEvaluacion;
    }

    public LocalDateTime getRespuestaEvaluacion() {
        return RespuestaEvaluacion;
    }

    public void setRespuestaEvaluacion(LocalDateTime respuestaEvaluacion) {
        RespuestaEvaluacion = respuestaEvaluacion;
    }
}
