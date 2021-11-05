package cl.ucn.dge.salud.visadocertificados.controller;

import cl.ucn.dge.salud.visadocertificados.service.ServicioDocumento;
import org.springframework.beans.factory.annotation.Autowired;
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
}
