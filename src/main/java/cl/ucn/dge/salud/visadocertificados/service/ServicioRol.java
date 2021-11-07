package cl.ucn.dge.salud.visadocertificados.service;


import cl.ucn.dge.salud.visadocertificados.model.Rol;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioRol {

    @Autowired
    private final RepositorioRol repositorioRol;

    public ServicioRol(RepositorioRol repositorioRol) {
        this.repositorioRol = repositorioRol;
    }

    public void guardarRol(Rol rol) {
        repositorioRol.save(rol);
    }
    public Optional<Rol> existeRolConNombre(Rol.enumRole nombre){
        return repositorioRol.existeRolPorNombre(nombre);
    }
}
