package cl.ucn.dge.salud.visadocertificados.repository;

import cl.ucn.dge.salud.visadocertificados.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RepositorioUser extends JpaRepository<User,Long> {

    @Query("SELECT s FROM User s WHERE s.correo = ?1")
    User buscarPorCorreo(String email);

    /**
    boolean findByEmail(String correo);

    boolean findByRut(String rut);
     */
}

