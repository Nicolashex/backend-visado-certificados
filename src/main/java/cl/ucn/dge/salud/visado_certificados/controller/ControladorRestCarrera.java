package cl.ucn.dge.salud.visado_certificados.controller;

import cl.ucn.dge.salud.visado_certificados.model.Carrera;
import cl.ucn.dge.salud.visado_certificados.service.ServicioCarrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpConnectTimeoutException;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class ControladorRestCarrera {

    @Autowired
    private final ServicioCarrera servicioCarrera;

    public ControladorRestCarrera(ServicioCarrera servicioCarrera) {
        this.servicioCarrera = servicioCarrera;
    }

    @GetMapping("/carreras")
    public List<Carrera> getCarreras() throws HttpConnectTimeoutException {
        return this.servicioCarrera.getCarreras();
    }
}
