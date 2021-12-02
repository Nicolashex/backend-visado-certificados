package cl.ucn.dge.salud.visadocertificados.setup;

import cl.ucn.dge.salud.visadocertificados.dto.RegistroAdminDto;
import cl.ucn.dge.salud.visadocertificados.dto.RegistroMedicoDto;
import cl.ucn.dge.salud.visadocertificados.dto.RegistroUserDto;
import cl.ucn.dge.salud.visadocertificados.model.Carrera;
import cl.ucn.dge.salud.visadocertificados.model.Rol;
import cl.ucn.dge.salud.visadocertificados.service.ServicioCarrera;
import cl.ucn.dge.salud.visadocertificados.service.ServicioRol;
import cl.ucn.dge.salud.visadocertificados.service.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent>{
    private boolean alreadySetup = false;

    @Autowired
    private ServicioRol servicioRol;
    @Autowired
    private ServicioCarrera servicioCarrera;
    @Autowired
    private ServicioUsuario servicioUsuario;



    /**
     * Set the database with the roles of the users,
     * also insert all the managers in the database.
     * @param event the event to respond to.
     */
    @Override
    @TransactionalEventListener
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }
        crearRol(Rol.enumRole.ROL_ESTUDIANTE);
        crearRol(Rol.enumRole.ROL_ADMINISTRADOR);
        crearRol(Rol.enumRole.ROL_MEDICO);

        crearAdmin("admin@correo.cl","12345678-9","contraseña","Admin","Admin",
                "Admin","+5612345678","Administrador","Administrador");

        crearMedico("medico1@correo.cl","111111-2","contraseña","Osvaldo",
                "Herrera","Urrutia","+569231412522","Personal evaluador","Medico general");

        crearMedico("medico2@correo.cl","222222-2","contraseña2","Josefina",
                "Rojo","Valdes","+569231412522","Personal evaluador","Dentista");

        crearMedico("medico3@correo.cl","3333333-2","contraseña3","Nicolas",
                "Pereira","Farfan","+569231412522","Personal evaluador","Psicologo");

        crearMedico("medico4@correo.cl","6666666-2","contraseña3","Paula",
                "Daza","Rojas","+569231412522","Personal evaluador","Medico general");

        crearCarrera("Analista Químico", "Antofagasta", "DISC");
        crearCarrera("Arquitectura", "Antofagasta", "DISC");
        crearCarrera("Contador Auditor - Contador Público Diurno", "Antofagasta", "DISC");
        crearCarrera("Contador Auditor - Contador Público Vespertino", "Antofagasta", "DISC");
        crearCarrera("Derecho", "Antofagasta", "DISC");
        crearCarrera("Geología", "Antofagasta", "DISC");
        crearCarrera("Ingeniería Civil Ambiental", "Antofagasta", "DISC");
        crearCarrera("Ingeniería Civil de Minas", "Antofagasta", "DISC");
        crearCarrera("Ingeniería Civil en Computación e Informática", "Antofagasta", "DISC");
        crearCarrera("Ingeniería civil en gestión de la construcción", "Antofagasta", "DISC");
        crearCarrera("Ingeniería Civil Industrial", "Antofagasta", "DISC");
        crearCarrera("Ingeniería Civil Metalúrgica", "Antofagasta", "DISC");
        crearCarrera("Ingeniería Civil Plan Común", "Antofagasta", "DISC");
        crearCarrera("Ingeniería Civil Química", "Antofagasta", "DISC");
        crearCarrera("Ingeniería civil", "Antofagasta", "DISC");
        crearCarrera("Ingeniería Comercial", "Antofagasta", "DISC");
        crearCarrera("Ingeniería en Computación e Informática", "Antofagasta", "DISC");
        crearCarrera("Ingeniería en Construcción", "Antofagasta", "DISC");
        crearCarrera("Ingeniería en Información y Control de Gestión", "Antofagasta", "DISC");
        crearCarrera("Ingeniería en Metalurgia", "Antofagasta", "DISC");
        crearCarrera("Licenciatura en Física con mención en Astronomía", "Antofagasta", "DISC");
        crearCarrera("Licenciatura en Matemática", "Antofagasta", "DISC");
        crearCarrera("Pedagogía en Educación Básica con Especialización", "Antofagasta", "DISC");
        crearCarrera("Pedagogía en Educación Parvularia con Mención en Desarrollo Emocional y Cognitivo", "Antofagasta", "DISC");
        crearCarrera("Pedagogía en Inglés", "Antofagasta", "DISC");
        crearCarrera("Pedagogía en Matemática en Educación Media", "Antofagasta", "DISC");
        crearCarrera("Periodismo", "Antofagasta", "DISC");
        crearCarrera("Programa de Prosecución de Estudios de Formación Pedagógica", "Antofagasta", "DISC");
        crearCarrera("Psicología", "Antofagasta", "DISC");
        crearCarrera("Química Industrial", "Antofagasta", "DISC");
        crearCarrera("Química y Farmacia", "Antofagasta", "DISC");
        alreadySetup = true;

    }

    @Transactional
    void crearRol(final Rol.enumRole nombre) {
        Optional<Rol> rol = servicioRol.existeRolConNombre(nombre);
        if (rol.isPresent()) {
            return;
        }
        Rol nuevoRol = new Rol(nombre);
        servicioRol.guardarRol(nuevoRol);
    }
    @Transactional
    void crearCarrera(final String nombre, final String sede,
                      final String departamento) {
        Optional<Carrera> carrera = servicioCarrera.existeCarreraPorNombre(nombre);
        if (carrera.isPresent()) {
            return;
        }
        Carrera nuevaCarrera = new Carrera(nombre, sede, departamento);
        servicioCarrera.guardarCarrera(nuevaCarrera);
    }

    @Transactional
    void crearMedico(final String correo, final String rut, final String contrasena,
                     final String nombre, final String primerApellido, final String segundoApellido,
                     final String telefono, final String cargo, final String profesion) {

        RegistroMedicoDto rg = new RegistroMedicoDto(correo,rut,contrasena,nombre,primerApellido,segundoApellido,
                profesion,telefono, cargo);

        servicioUsuario.crearMedico(rg);

    }

    void crearAdmin(final String correo, final String rut, final String contrasena,
                    final String nombre, final String primerApellido, final String segundoApellido,
                    final String telefono, final String cargo, final String profesion){

        RegistroAdminDto rg = new RegistroAdminDto(correo,rut,contrasena,nombre,primerApellido,segundoApellido,telefono,
                cargo,profesion);

        servicioUsuario.crearAdmin(rg);

    }


}
