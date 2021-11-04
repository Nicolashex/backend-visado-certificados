package cl.ucn.dge.salud.visado_certificados.service;

import cl.ucn.dge.salud.visado_certificados.repository.RepositorioDocumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioDocumento {

    @Autowired
    private final RepositorioDocumento repositorioDocumento;

    public ServicioDocumento(RepositorioDocumento repositorioDocumento) {
        this.repositorioDocumento = repositorioDocumento;
    }
}
