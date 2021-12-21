package cl.ucn.dge.salud.visadocertificados.controller;

import cl.ucn.dge.salud.visadocertificados.model.Certificado;
import cl.ucn.dge.salud.visadocertificados.model.Documento;
import cl.ucn.dge.salud.visadocertificados.service.ServicioCertificado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/v1")
public class ControladorRestCertificado {

    @Autowired
    private final ServicioCertificado servicioCertificado;

    public ControladorRestCertificado(ServicioCertificado servicioCertificado) {
        this.servicioCertificado = servicioCertificado;
    }

    @GetMapping("/certificado/{id}")
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

}
