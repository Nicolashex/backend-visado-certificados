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

    @Column(name="rutMedicoTratante",nullable = false)
    private String rutMedicoTratante;

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

    //TODO: Eliminar ya no se utiliza
    @Column(name="resolucion",columnDefinition = "varchar(255) default 'Por evaluar'" )
    private String resolucion;

    @Column(name="comentario")
    private String comentario;

    //TODO:Eliminar ya no se utiliza
    @JsonProperty("comentario_medico")
    @Column(name="comentario_medico")
    private String comentarioMedico;

    @JsonProperty("fecha_inicio_solicitud")
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

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User estudiante;

    @ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User idProfesional;

    public Solicitud() {
    }

    public Solicitud(String rutMedicoTratante, String nombreMedicoTratante, LocalDate fechaInicioReposo,
                     LocalDate fechaFinReposo, String motivo, String rutCarga, List<Documento> documentos,
                     User estudiante, boolean esCarga, String nombreCarga) {
        this.rutMedicoTratante = rutMedicoTratante;
        this.nombreMedicoTratante = nombreMedicoTratante;
        this.fechaInicioReposo = fechaInicioReposo;
        this.fechaFinReposo = fechaFinReposo;
        this.motivo = motivo;
        this.rutCarga = rutCarga;
        this.documentos = documentos;
        this.estudiante = estudiante;
        this.esCarga = esCarga;
        this.nombreCarga = nombreCarga;
        this.estado = estadosPosibles.INGRESADO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getComentarioMedico() {
        return comentarioMedico;
    }

    public void setComentarioMedico(String comentarioMedico) {
        this.comentarioMedico = comentarioMedico;
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

    public String getRutMedicoTratante() {
        return rutMedicoTratante;
    }

    public void setRutMedicoTratante(String rutMedicoTratante) {
        this.rutMedicoTratante = rutMedicoTratante;
    }

    public User getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(User idProfesional) {
        this.idProfesional = idProfesional;
    }


    public enum estadosPosibles {

        INGRESADO,
        EN_EVALUACION,
        EN_CORRECCION,
        CORREGIDO,
        APROBADO,
        RECHAZADO,

    }

}
