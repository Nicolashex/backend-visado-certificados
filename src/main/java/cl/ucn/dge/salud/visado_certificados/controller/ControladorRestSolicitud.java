package cl.ucn.dge.salud.visado_certificados.controller;

import cl.ucn.dge.salud.visado_certificados.service.ServicioSolicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ControladorRestSolicitud {

    @Autowired
    private final ServicioSolicitud servicioSolicitud;

    public ControladorRestSolicitud(ServicioSolicitud servicioSolicitud) {
        this.servicioSolicitud = servicioSolicitud;
    }
}
