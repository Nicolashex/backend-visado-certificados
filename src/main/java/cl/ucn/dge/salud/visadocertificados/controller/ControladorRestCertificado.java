package cl.ucn.dge.salud.visadocertificados.controller;

import cl.ucn.dge.salud.visadocertificados.dto.RespuestaValidacionDto;
import cl.ucn.dge.salud.visadocertificados.model.Certificado;
import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import cl.ucn.dge.salud.visadocertificados.service.ServicioCertificado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ControladorRestCertificado {

    @Autowired
    private final ServicioCertificado servicioCertificado;

    public ControladorRestCertificado(ServicioCertificado servicioCertificado) {
        this.servicioCertificado = servicioCertificado;
    }

    @GetMapping("/certificados/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Certificado certificado = servicioCertificado.getCertificadoPorId(id);
        String aux = "pdf";
        byte[] data = this.servicioCertificado.crearPDF(
                servicioCertificado.getSolicitudByCertificadoID(certificado.getId()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + certificado.getId() + "." + aux +"\"")
                .body(data);
    }
    @GetMapping("/certificados/validar/{id}")
    public RespuestaValidacionDto validarCertificado(@PathVariable String id){
        boolean esValido = false;
        String mensaje ="";
        if(this.servicioCertificado.existeCertificado(id).isPresent()){
            esValido = true;
            Solicitud solicitud = this.servicioCertificado.getCertificadoPorId(id).getSolicitud();
            mensaje = "El certificado es valido y fue emitido en la fecha: " +
                    solicitud.getFechaFinSolicitud().toLocalDate() + " para el alumno con RUT:" +
                    solicitud.getEstudiante().getRut() + " con fecha de reposo de termino para: "+
                    solicitud.getFechaFinReposo() + ".";
        }
        else{
            mensaje = "El certificado no es valido";
        }
        RespuestaValidacionDto respuesta = new RespuestaValidacionDto(esValido, mensaje);
        return respuesta;
    }

}
