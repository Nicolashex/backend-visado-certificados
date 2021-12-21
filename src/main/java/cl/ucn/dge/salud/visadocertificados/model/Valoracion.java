package cl.ucn.dge.salud.visadocertificados.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "valoraciones")
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,unique = true)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_solicitud",referencedColumnName = "id")
    private Solicitud solicitud;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="fechaValoracion",nullable = false)
    private LocalDateTime fechaEvaluacion;

    @Column(name="valoracion_1")
    private Integer valoracion1;

    @Column(name="valoracion_2")
    private Integer valoracion2;

    @Column(name="valoracion_3")
    private Integer valoracion3;

    @Column(name="comentario",nullable = true)
    private String comentario;

    public Valoracion(){

    }

    public Valoracion(Solicitud solicitud, LocalDateTime fechaEvaluacion, Integer valoracion1, Integer valoracion2,
                      Integer valoracion3, String comentario) {

        this.solicitud = solicitud;
        this.fechaEvaluacion = fechaEvaluacion;
        this.valoracion1 = valoracion1;
        this.valoracion2 = valoracion2;
        this.valoracion3 = valoracion3;
        this.comentario = comentario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public LocalDateTime getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(LocalDateTime fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
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
