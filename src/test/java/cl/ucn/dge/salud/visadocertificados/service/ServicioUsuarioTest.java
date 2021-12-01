package cl.ucn.dge.salud.visadocertificados.service;

import cl.ucn.dge.salud.visadocertificados.dto.RegistroUserDto;
import cl.ucn.dge.salud.visadocertificados.model.Carrera;
import cl.ucn.dge.salud.visadocertificados.model.Rol;
import cl.ucn.dge.salud.visadocertificados.model.User;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioCarrera;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioRol;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ServicioUsuarioTest {

    @InjectMocks
    ServicioUsuario servicioUsuario;

    @Mock
    private RepositorioUser repositorioUser;
    @Mock
    private RepositorioCarrera repositorioCarrera;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RepositorioRol repositoriRol;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void crearUsuario(){
        RegistroUserDto registroUserDto = new RegistroUserDto("correo", "rut", "contrasenia",
                "nombre", "primer_apellido", "segundo_apellido", "telefono",
                "carrera", "cargo", "profesion");
        Rol.enumRole rol = Rol.enumRole.ROL_ESTUDIANTE;
        Rol nuevoRol = new Rol(rol);
        Carrera carrera = new Carrera("nombre", "sede", "departamento");
        when(repositoriRol.findByName(any(Rol.enumRole.class))).thenReturn(nuevoRol);
        when(repositorioCarrera.carreraByNombre(any(String.class))).thenReturn(carrera);
        when(passwordEncoder.encode(any(String.class))).thenReturn("contraseniacodificada");
        when(repositorioUser.save(any(User.class))).thenReturn(new User());

        assertNotNull(servicioUsuario.save(registroUserDto));

    }
}
