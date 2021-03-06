package cl.ucn.dge.salud.visadocertificados.service;

import cl.ucn.dge.salud.visadocertificados.cuerpo_entidad.CuerpoValoracion;
import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import cl.ucn.dge.salud.visadocertificados.model.Valoracion;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioValoracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicioValoracion {

    @Autowired
    private final RepositorioValoracion repositorioValoracion;

    @Autowired
    private final ServicioSolicitud servicioSolicitud;


    public ServicioValoracion(RepositorioValoracion repositorioValoracion, ServicioSolicitud servicioSolicitud) {
        this.repositorioValoracion = repositorioValoracion;
        this.servicioSolicitud = servicioSolicitud;
    }

    @Transactional
    public Valoracion registrarValoracion(final @RequestBody CuerpoValoracion cuerpoValoracion) throws IOException {

        Solicitud solicitud = this.servicioSolicitud.getSolicitudById(cuerpoValoracion.getIdSolicitud());

        if (solicitud == null) {
            throw new IOException("Solicitud invalida");
        } else if (!solicitud.getEstado().equals(Solicitud.estadosPosibles.APROBADO)) {
            throw new IOException("Solicitud no finalizada");
        } else if(hasValidacion(solicitud.getId())){
            throw new IOException("Solicitud ya cuenta con valoracion");
        }else if (cuerpoValoracion.getValoracion1() == null ||
                    cuerpoValoracion.getValoracion2() == null ||
                    cuerpoValoracion.getValoracion3() == null){
            throw new IOException("Se necesitan valoraciones");
        }

        Valoracion valoracion = new Valoracion(
                solicitud,
                LocalDateTime.now(),
                cuerpoValoracion.getValoracion1(),
                cuerpoValoracion.getValoracion2(),
                cuerpoValoracion.getValoracion3(),
                cuerpoValoracion.getComentario());

        solicitud.setValorado(true);

        return repositorioValoracion.save(valoracion);
    }

    @Transactional
    public boolean hasValidacion(Long idSolicitud){

        Solicitud solicitud = this.servicioSolicitud.getSolicitudById(idSolicitud);

        if(this.repositorioValoracion.existsBySolicitud(solicitud)){
            return true;
        }
        return false;
    }

    public List<Valoracion> getAllValoraciones(){
        return this.repositorioValoracion.findAll();
    }

}
