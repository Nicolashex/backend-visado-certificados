package cl.ucn.dge.salud.visadocertificados.service;

import cl.ucn.dge.salud.visadocertificados.cuerpo_entidad.CuerpoSolicitud;
import cl.ucn.dge.salud.visadocertificados.model.Documento;
import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import cl.ucn.dge.salud.visadocertificados.model.User;
import cl.ucn.dge.salud.visadocertificados.projection.SolicitudDetalladaAdministrador;
import cl.ucn.dge.salud.visadocertificados.projection.SolicitudResumenAdministrador;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioSolicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ServicioSolicitud {



    @Autowired
    private final RepositorioSolicitud repositorioSolicitud;

    @Autowired
    private  final ServicioDocumento servicioDocumento;

    @Autowired
    private  final ServicioUsuario servicioUsuario;

    @Autowired
    private final ServicioCarrera servicioCarrera;

    public ServicioSolicitud(RepositorioSolicitud repositorioSolicitud,
                             ServicioDocumento servicioDocumento,
                             ServicioUsuario servicioUsuario, ServicioCarrera servicioCarrera) {
        this.repositorioSolicitud = repositorioSolicitud;
        this.servicioDocumento = servicioDocumento;
        this.servicioUsuario = servicioUsuario;
        this.servicioCarrera = servicioCarrera;
    }
    @Transactional
    public Solicitud ingresarSolicitud(final @RequestBody CuerpoSolicitud solicitud,
                                       final String correoUsuario,
                                       @RequestPart MultipartFile[] certificado,
                                       @RequestPart MultipartFile[] respaldo) throws InternalError, IOException {


        User estudiante = servicioUsuario.getUsuarioPorCorreo(correoUsuario);
        if(solicitud.esCarga()) {
            if(solicitud.getNombreCarga()==null || solicitud.getRutCarga()==null){
                throw new IOException("Se requiere un nombre y rut para la carga asociada");
            }else if (solicitud.getNombreCarga().isBlank() || solicitud.getRutCarga().isBlank()) {
                throw new IOException("Se requiere un nombre y rut para la carga asociada");
            }

        }
        List<Documento> documentos ;
        try{
            documentos =servicioDocumento.guardarDocumento(certificado, respaldo);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new InternalError("No se pudieron guardar los archivos");
        }
        String rutCarga =null;
        String nombreCarga =null;
        if(solicitud.esCarga()){
            rutCarga = solicitud.getRutCarga();
            nombreCarga = solicitud.getNombreCarga();
        }

        Solicitud solicitudNueva = new Solicitud(solicitud.getNombreMedicoTratante(),solicitud.getFechaInicioReposo(),
                solicitud.getFechaFinReposo(), solicitud.getMotivo(), rutCarga, documentos, estudiante,
                solicitud.esCarga(), nombreCarga);
        return repositorioSolicitud.save(solicitudNueva);

    }
    public List<SolicitudResumenAdministrador> getSolicitudes(){
        return this.repositorioSolicitud.getSolicitudByEstado(Solicitud.estadosPosibles.REVISION_PRERREQUISITOS);
    }
    public List<Solicitud> getAllSolicitudes(){
        return this.repositorioSolicitud.findAll();
    }

    public SolicitudDetalladaAdministrador getSolicitudDetalladaAdministrador(Long id) {
        return this.repositorioSolicitud.getSolicitudDetalladaAdministrador(id);
    }
}
