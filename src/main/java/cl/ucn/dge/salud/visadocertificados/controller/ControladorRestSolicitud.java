package cl.ucn.dge.salud.visadocertificados.controller;

import cl.ucn.dge.salud.visadocertificados.cuerpo_entidad.CuerpoSolicitud;
import cl.ucn.dge.salud.visadocertificados.service.ServicioSolicitud;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1")
public class ControladorRestSolicitud {

    @Autowired
    private final ServicioSolicitud servicioSolicitud;



    public ControladorRestSolicitud(ServicioSolicitud servicioSolicitud) {
        this.servicioSolicitud = servicioSolicitud;
    }

    @PostMapping(value = "/solicitudes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<String> registerNewTicket(@RequestPart CuerpoSolicitud solicitud,
                                                    @RequestParam("certificado") MultipartFile[] certificado,
                                                    @RequestParam("respaldo") MultipartFile[] respaldo) throws JsonProcessingException {
        String mensaje = "";
        try {
            servicioSolicitud.ingresarSolicitud(solicitud, certificado, respaldo);
            mensaje = "Solicitud creada de forma exitosa";
            return ResponseEntity.status(HttpStatus.OK).body(mensaje);

        } catch (Exception e){
            mensaje = "Solicitud no pudo ser creada";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(mensaje);

        }

    }
}
