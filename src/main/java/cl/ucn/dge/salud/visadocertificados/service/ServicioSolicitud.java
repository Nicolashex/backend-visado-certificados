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
import java.util.Locale;

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
        if (!estudiante.getRut().equalsIgnoreCase(solicitud.getRutPaciente())){
            throw new IOException("Rut no coinciden");
        }
        if(!this.servicioCarrera.existeCarreraPorNombre(solicitud.getCarrera()).isPresent()){
            throw new IOException("No existe carrera");
        }
        if(solicitud.esCarga() && (solicitud.getNombreCarga()==null ||
                solicitud.getRutCarga()==null)) {
            throw new IOException("Se requiere un nombre y rut para la carga asociada");
        }
        if(solicitud.getNombreCarga().isBlank() ||
                solicitud.getRutCarga().isBlank()) {
            throw new IOException("nombre y rut para la carga asociada no pueden estar en blanco");
        }
        List<Documento> documentos ;
        try{
            documentos =servicioDocumento.guardarDocumento(certificado, respaldo);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new InternalError("No se pudieron guardar los archivos");
        }
        Solicitud solicitudNueva = new Solicitud(solicitud.getNombrePaciente(),solicitud.getRutPaciente(),
                solicitud.getCarrera(),solicitud.getNombreMedicoTratante(),solicitud.getFechaInicioReposo(),
                solicitud.getFechaFinReposo(), solicitud.getMotivo(), solicitud.getRutCarga(), documentos, estudiante,
                solicitud.esCarga(), solicitud.getNombreCarga());
        return repositorioSolicitud.save(solicitudNueva);

    }
}
