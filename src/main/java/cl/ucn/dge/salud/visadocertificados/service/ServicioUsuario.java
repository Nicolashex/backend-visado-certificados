package cl.ucn.dge.salud.visadocertificados.service;

import cl.ucn.dge.salud.visadocertificados.dto.RegistroUserDto;
import cl.ucn.dge.salud.visadocertificados.model.Carrera;
import cl.ucn.dge.salud.visadocertificados.model.Rol;
import cl.ucn.dge.salud.visadocertificados.model.User;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioCarrera;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioRol;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class ServicioUsuario {
    final private RepositorioUser repositorioUser;
    final private RepositorioCarrera repositorioCarrera;
    private final PasswordEncoder passwordEncoder;
    private final RepositorioRol repositoriRol;

    public ServicioUsuario(RepositorioUser repositorioUser,
                           RepositorioCarrera repositorioCarrera,
                           PasswordEncoder passwordEncoder,
                           RepositorioRol repositoriRol) {
        this.repositorioUser = repositorioUser;
        this.repositorioCarrera = repositorioCarrera;
        this.passwordEncoder = passwordEncoder;
        this.repositoriRol = repositoriRol;
    }

    public User getUserByCorro(String correo){
        return repositorioUser.buscarPorCorreo(correo);
    }

    public User getUsuarioById(Long idAlumno) {
        return repositorioUser.getById(idAlumno);
    }
    public Optional<User> findUsersByEmail (String correo){
        return repositorioUser.findUserByCorreo(correo);
    }

    public boolean rutDisponible(String rut) {
        return this.repositorioUser.findUserByRut(rut).isPresent();
    }

    public boolean correoDisponible(String correo) {
        return this.repositorioUser.findUserByCorreo(correo).isPresent();
    }

    public User save(RegistroUserDto registroUserDto) {

        Carrera c = repositorioCarrera.getCarreraById((long) registroUserDto.getCarrera());
        Rol rol = repositoriRol.findByName(Rol.enumRole.ROL_ESTUDIANTE);
        System.out.println(registroUserDto);
        User user = new User(
                registroUserDto.getCorreo(),
                registroUserDto.getRut(),
                passwordEncoder.encode(registroUserDto.getContrasena()), //Cifrar la contraseña
                registroUserDto.getNombre(),
                registroUserDto.getPrimerApellido(),
                registroUserDto.getSegundoApellido(),
                registroUserDto.getTelefono(),
                c,
                registroUserDto.getCargo(),
                registroUserDto.getProfesion(),
                rol);

        return repositorioUser.save(user);
    }
}
