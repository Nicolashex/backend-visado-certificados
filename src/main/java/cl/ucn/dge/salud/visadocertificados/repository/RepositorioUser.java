package cl.ucn.dge.salud.visadocertificados.repository;

import cl.ucn.dge.salud.visadocertificados.model.Rol;
import cl.ucn.dge.salud.visadocertificados.model.User;
import cl.ucn.dge.salud.visadocertificados.projection.MedicoResumen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RepositorioUser extends JpaRepository<User,Long> {

    @Query("SELECT s FROM User s WHERE s.correo = ?1")
    User buscarPorCorreo(String email);


    @Query("SELECT s FROM User s WHERE s.correo = ?1")
    Optional<User> findUserByCorreo(String correo);

    boolean existsByCorreo(@Param("correo") String correo);

    boolean existsByRut(@Param("rut") String rut);

    @Query("SELECT s FROM User s WHERE s.rut = ?1")
    Optional<User>  findUserByRut(String rut);

    @Query("SELECT s FROM User s WHERE s.rol.name=?1")
    List<MedicoResumen> getUserByRole(Rol.enumRole rolMedico);

    @Query("SELECT s FROM User s WHERE s.id =?1 and s.rol.name=?2")
    Optional<User> findUserByIdAndRol(Long id, Rol.enumRole rolMedico);

    /**
    boolean findByEmail(String correo);

    boolean findByRut(String rut);
     */

}

