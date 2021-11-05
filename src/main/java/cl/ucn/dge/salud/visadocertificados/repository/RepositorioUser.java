package cl.ucn.dge.salud.visadocertificados.repository;

import cl.ucn.dge.salud.visadocertificados.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RepositorioUser extends JpaRepository<User,Long> {

    @Query("SELECT s FROM User s WHERE s.correo = ?1")
    User buscarPorCorreo(String email);


    User findUserByCorreo(@Param("correo") String correo);

    boolean existsByCorreo(@Param("correo") String correo);

    boolean existsByRut(@Param("rut") String rut);

    /**
    boolean findByEmail(String correo);

    boolean findByRut(String rut);
     */
}

