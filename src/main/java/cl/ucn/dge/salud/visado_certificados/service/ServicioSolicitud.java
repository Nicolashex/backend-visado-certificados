package cl.ucn.dge.salud.visado_certificados.service;

import cl.ucn.dge.salud.visado_certificados.repository.RepositorioSolicitud;
import org.springframework.stereotype.Service;

@Service
public class ServicioSolicitud {

    private final RepositorioSolicitud repositorioSolicitud;

    public ServicioSolicitud(RepositorioSolicitud repositorioSolicitud) {
        this.repositorioSolicitud = repositorioSolicitud;
    }
}
