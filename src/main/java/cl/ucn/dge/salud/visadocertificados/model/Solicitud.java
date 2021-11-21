package cl.ucn.dge.salud.visadocertificados.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="fechaInicioReposo",nullable = false)
    private LocalDate fechaInicioReposo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="fechaFinReposo",nullable = false)
    private LocalDate fechaFinReposo;

    @Column(name="motivo",nullable = false,length = 255)
    private String motivo;

    @Column(name="es_carga",nullable = false)
    @JsonProperty("es_carga")
    private boolean esCarga;

    @Column(name="rut_carga", nullable = true, length = 15)
    @JsonProperty("rut_carga")
    private String rutCarga;

    @Column(name="nombre_carga", nullable = true)
    @JsonProperty("nombre_carga")
    private String nombreCarga;

    @Enumerated(EnumType.STRING)
    @Column(name="estado",columnDefinition = "varchar(255) default 'Ingresada'" )
    private estadosPosibles estado;

    @Column(name="resolucion",columnDefinition = "varchar(255) default 'Por evaluar'" )
    private String resolucion;

    @Column(name="comentario")
    private String comentario;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    @Column(name="fechaInicioSolicitud",nullable = false)
    private LocalDateTime fechaInicioSolicitud;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="fechaFinSolicitud")
    private LocalDateTime fechaFinSolicitud;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="IngresoEvaluacion")
    private LocalDateTime IngresoEvaluacion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="RespuestaEvaluacion")
    private LocalDateTime RespuestaEvaluacion;

    @OneToMany(targetEntity = Documento.class, cascade=CascadeType.ALL)
    @JoinColumn(name = "solicitud",referencedColumnName = "id")
    private List<Documento> documentos;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User estudiante;

    public Solicitud() {
    }

    public Solicitud(String nombrePaciente, String rutPaciente, String carrera,
                     String nombreMedicoTratante, LocalDate fechaInicioReposo,
                     LocalDate fechaFinReposo, String motivo, String rutCarga, List<Documento> documentos,
                     User estudiante, boolean esCarga, String nombreCarga) {
        this.nombrePaciente = nombrePaciente;
        this.rutPaciente = rutPaciente;
        this.carrera = carrera;
        this.nombreMedicoTratante = nombreMedicoTratante;
        this.fechaInicioReposo = fechaInicioReposo;
        this.fechaFinReposo = fechaFinReposo;
        this.motivo = motivo;
        this.rutCarga = rutCarga;
        this.documentos = documentos;
        this.estudiante = estudiante;
        this.esCarga = esCarga;
        this.nombreCarga = nombreCarga;
        this.estado = estadosPosibles.REVISION_PRERREQUISITOS;
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

    public boolean isEsCarga() {
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

    public estadosPosibles getEstado() {
        return estado;
    }

    public void setEstado(estadosPosibles estado) {
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

    public enum estadosPosibles {
        REVISION_PRERREQUISITOS,
        REVISION_DATOS
    }

}
