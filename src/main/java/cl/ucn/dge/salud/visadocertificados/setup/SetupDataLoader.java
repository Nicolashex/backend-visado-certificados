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
        crearRol(Rol.enumRole.ROLE_ADMINISTRADOR);

        crearCarrera("ICCI", "Antofagasta", "DISC");
        crearCarrera("ICI", "Antofagasta", "DISC");
        crearCarrera("ASDA", "Antofagasta", "DISC");
        crearCarrera("FAC", "Antofagasta", "DISC");
        crearCarrera("IMA", "Antofagasta", "DISC");
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
