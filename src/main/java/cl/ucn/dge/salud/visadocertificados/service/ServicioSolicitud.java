package cl.ucn.dge.salud.visadocertificados.service;

import cl.ucn.dge.salud.visadocertificados.repository.RepositorioSolicitud;
import org.springframework.stereotype.Service;

@Service
public class ServicioSolicitud {

    private final RepositorioSolicitud repositorioSolicitud;

    public ServicioSolicitud(RepositorioSolicitud repositorioSolicitud) {
        this.repositorioSolicitud = repositorioSolicitud;
    }
}
