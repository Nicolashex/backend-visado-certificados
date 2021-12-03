package cl.ucn.dge.salud.visadocertificados.dto;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class ModificarSolicitudEstudianteDto {

    private String nombreCarga;

    private String rutCarga;

    private String diagnostico;

    private String nombreMedicoTratante;

    private String rutMedicoTratante;

    private String fechaInicioReposo;

    private String fechaFinReposo;

    @NotBlank
    private List<String> listaIdDocumento;

    public ModificarSolicitudEstudianteDto() {
    }

    public ModificarSolicitudEstudianteDto(String nombreCarga, String rutCarga, String diagnostico,
                                           String nombreMedicoTratante, String rutMedicoTratante,
                                           String fechaInicioReposo, String fechaFinReposo,
                                           List<String> listaIdDocumento) {
        this.nombreCarga = nombreCarga;
        this.rutCarga = rutCarga;
        this.diagnostico = diagnostico;
        this.nombreMedicoTratante = nombreMedicoTratante;
        this.rutMedicoTratante = rutMedicoTratante;
        this.fechaInicioReposo = fechaInicioReposo;
        this.fechaFinReposo = fechaFinReposo;
        this.listaIdDocumento = listaIdDocumento;
    }

    public String getNombreCarga() {
        return nombreCarga;
    }

    public void setNombreCarga(String nombreCarga) {
        this.nombreCarga = nombreCarga;
    }

    public String getRutCarga() {
        return rutCarga;
    }

    public void setRutCarga(String rutCarga) {
        this.rutCarga = rutCarga;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getNombreMedicoTratante() {
        return nombreMedicoTratante;
    }

    public void setNombreMedicoTratante(String nombreMedicoTratante) {
        this.nombreMedicoTratante = nombreMedicoTratante;
    }

    public String getRutMedicoTratante() {
        return rutMedicoTratante;
    }

    public void setRutMedicoTratante(String rutMedicoTratante) {
        this.rutMedicoTratante = rutMedicoTratante;
    }

    public String getFechaInicioReposo() {
        return fechaInicioReposo;
    }

    public void setFechaInicioReposo(String fechaInicioReposo) {
        this.fechaInicioReposo = fechaInicioReposo;
    }

    public String getFechaFinReposo() {
        return fechaFinReposo;
    }

    public void setFechaFinReposo(String fechaFinReposo) {
        this.fechaFinReposo = fechaFinReposo;
    }

    public List<String> getListaIdDocumento() {
        return listaIdDocumento;
    }

    public void setListaIdDocumento(List<String> listaIdDocumento) {
        this.listaIdDocumento = listaIdDocumento;
    }


}
