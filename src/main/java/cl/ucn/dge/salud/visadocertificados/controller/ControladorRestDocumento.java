package cl.ucn.dge.salud.visadocertificados.controller;

import cl.ucn.dge.salud.visadocertificados.model.Documento;
import cl.ucn.dge.salud.visadocertificados.service.ServicioDocumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ControladorRestDocumento {


    @Autowired
    private final ServicioDocumento servicioDocumento;

    public ControladorRestDocumento(ServicioDocumento servicioDocumento) {
        this.servicioDocumento = servicioDocumento;
    }

    @GetMapping("/documentos/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Documento documento = servicioDocumento.getDocumentoPorId(id);
        String extesionArchivo =  documento.getTipoArchivo();
        String aux = extesionArchivo.substring(extesionArchivo.lastIndexOf("/") + 1);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + documento.getId() + "." + aux +"\"")
                .body(documento.getData());
    }

}
