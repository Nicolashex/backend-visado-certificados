package cl.ucn.dge.salud.visadocertificados.repository;

import cl.ucn.dge.salud.visadocertificados.model.Rol;
import cl.ucn.dge.salud.visadocertificados.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RepositorioRol extends JpaRepository<Rol, Long> {

    Rol findByName(Rol.enumRole name);

    @Query("SELECT r FROM role r WHERE r.name=?1 ")
    Optional<Rol> existeRolPorNombre(Rol.enumRole nombre);
}
