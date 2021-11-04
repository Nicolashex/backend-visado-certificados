package cl.ucn.dge.salud.visado_certificados.service;

import cl.ucn.dge.salud.visado_certificados.model.Carrera;
import cl.ucn.dge.salud.visado_certificados.repository.RepositorioCarrera;
import cl.ucn.dge.salud.visado_certificados.response.RespuestaRestCarrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.http.HttpConnectTimeoutException;
import java.util.List;

@Service
public class ServicioCarrera {

    @Autowired
    private final RepositorioCarrera repositorioCarrera;


    public ServicioCarrera(RepositorioCarrera repositorioCarrera) {
        this.repositorioCarrera = repositorioCarrera;
    }

    @Transactional(readOnly = true)
    public List<Carrera> getCarreras() throws HttpConnectTimeoutException {

        List<Carrera> respuesta;

        try {
            respuesta = repositorioCarrera.findAll();
        } catch (Exception e) {
            throw new HttpConnectTimeoutException("Error con el servidor");

        }
        return respuesta;
    }
}
