package cl.ucn.dge.salud.visadocertificados.service;

import cl.ucn.dge.salud.visadocertificados.model.Carrera;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioCarrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.http.HttpConnectTimeoutException;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioCarrera {

    @Autowired
    private final RepositorioCarrera repositorioCarrera;


    public ServicioCarrera(RepositorioCarrera repositorioCarrera) {
        this.repositorioCarrera = repositorioCarrera;
    }

    @Transactional(readOnly = true)
    public List<Carrera> getCarreras() throws HttpConnectTimeoutException {

        List<Carrera> respuesta;

        try {
            respuesta = repositorioCarrera.findAll();
        } catch (Exception e) {
            throw new HttpConnectTimeoutException("Error con el servidor");

        }
        return respuesta;
    }

    private Carrera getCarreraById(Long id) throws HttpConnectTimeoutException {
        Carrera respuesta;
        try{
            respuesta =  repositorioCarrera.getCarreraById(id);
        }catch(Exception e){
            throw new HttpConnectTimeoutException("Error con el servidor");
        }

        return respuesta;

    }

    public Optional<Carrera> existeCarreraPorNombre(String nombre) {
        return repositorioCarrera.findByNombre(nombre);
    }

    public void guardarCarrera(Carrera nuevaCarrera) {
        repositorioCarrera.save(nuevaCarrera);
    }

}
