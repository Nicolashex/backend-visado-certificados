package cl.ucn.dge.salud.visadocertificados.service;

import cl.ucn.dge.salud.visadocertificados.cuerpo_entidad.CuerpoSolicitud;
import cl.ucn.dge.salud.visadocertificados.model.Documento;
import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import cl.ucn.dge.salud.visadocertificados.model.User;
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
    private  final ServicioUserImpl servicioUsuario;

    public ServicioSolicitud(RepositorioSolicitud repositorioSolicitud,
                             ServicioDocumento servicioDocumento,
                             ServicioUserImpl servicioUsuario) {
        this.repositorioSolicitud = repositorioSolicitud;
        this.servicioDocumento = servicioDocumento;
        this.servicioUsuario = servicioUsuario;
    }
    @Transactional
    public Solicitud ingresarSolicitud(final @RequestBody CuerpoSolicitud solicitud,
                                       @RequestPart MultipartFile[] certificado,
                                       @RequestPart MultipartFile[] respaldo)  {
        List<Documento> documentos ;
        try{
            documentos =servicioDocumento.guardarDocumento(certificado, respaldo);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new InternalError("No se pudieron guardar los archivos");
        }
        User estudiante = servicioUsuario.getUsuarioById(solicitud.getIdAlumno());
        Solicitud solicitudNueva = new Solicitud(solicitud.getNombrePaciente(),solicitud.getRutPaciente(),
                solicitud.getCarrera(),solicitud.getNombreMedicoTratante(),solicitud.getFechaInicioReposo(),
                solicitud.getFechaFinReposo(), solicitud.getMotivo(), documentos, estudiante);
        return repositorioSolicitud.save(solicitudNueva);


    }
}
