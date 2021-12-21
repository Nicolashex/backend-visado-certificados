package cl.ucn.dge.salud.visadocertificados.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "certificado")
public class Certificado {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @OneToOne(mappedBy = "certificado")
    private Solicitud solicitud;

    @JsonIgnore
    @Lob
    private byte[] certificado;

    public Certificado() {
    }

    public Certificado(String id, byte[] certificado) {
        this.id = id;
        this.certificado = certificado;
    }

    public String getId() {
        return id;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getCertificado() {
        return certificado;
    }

    public void setCertificado(byte[] certificado) {
        this.certificado = certificado;
    }
}
