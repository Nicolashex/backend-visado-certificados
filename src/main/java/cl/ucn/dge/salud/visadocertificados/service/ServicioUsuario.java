package cl.ucn.dge.salud.visadocertificados.service;

import cl.ucn.dge.salud.visadocertificados.dto.RegistroMedicoDto;
import cl.ucn.dge.salud.visadocertificados.dto.RegistroUserDto;
import cl.ucn.dge.salud.visadocertificados.model.Carrera;
import cl.ucn.dge.salud.visadocertificados.model.Rol;
import cl.ucn.dge.salud.visadocertificados.model.User;
import cl.ucn.dge.salud.visadocertificados.projection.MedicoResumen;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioCarrera;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioRol;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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

    public User getUsuarioPorCorreo(String correo){
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

        Carrera carrera = repositorioCarrera.carreraByNombre(registroUserDto.getCarrera());
        Rol rol = repositoriRol.findByName(Rol.enumRole.ROL_ESTUDIANTE);

        User user = new User(
                registroUserDto.getCorreo(),
                registroUserDto.getRut(),
                passwordEncoder.encode(registroUserDto.getContrasena()), //Cifrar la contraseña
                registroUserDto.getNombre(),
                registroUserDto.getPrimerApellido(),
                registroUserDto.getSegundoApellido(),
                registroUserDto.getTelefono(),
                carrera,
                //registroUserDto.getCargo(),
                //registroUserDto.getProfesion(),
                rol);
        return repositorioUser.save(user);
    }

    public User crearMedico(RegistroMedicoDto registroMedicoDto){

        User user = new User();

        user.setCorreo(registroMedicoDto.getCorreo());
        user.setRut(registroMedicoDto.getRut());
        user.setContrasena(passwordEncoder.encode(registroMedicoDto.getContrasena()));
        user.setNombre(registroMedicoDto.getNombre());
        user.setPrimerApellido(registroMedicoDto.getPrimerApellido());
        user.setSegundoApellido(registroMedicoDto.getSegundoApellido());
        user.setTelefono(registroMedicoDto.getTelefono());
        user.setRoles(repositoriRol.findByName(Rol.enumRole.ROL_MEDICO));
        user.setProfesion(registroMedicoDto.getProfesion());
        user.setCargo(registroMedicoDto.getCargo());

        return repositorioUser.save(user);
    }

    public List<MedicoResumen> getMedicosResumen() {
        return this.repositorioUser.getUserByRole(Rol.enumRole.ROL_MEDICO);
    }
}
