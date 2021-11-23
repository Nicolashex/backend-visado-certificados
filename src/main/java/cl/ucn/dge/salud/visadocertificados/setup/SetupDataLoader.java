package cl.ucn.dge.salud.visadocertificados.setup;

import cl.ucn.dge.salud.visadocertificados.model.Carrera;
import cl.ucn.dge.salud.visadocertificados.model.Rol;
import cl.ucn.dge.salud.visadocertificados.service.ServicioCarrera;
import cl.ucn.dge.salud.visadocertificados.service.ServicioRol;
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



}