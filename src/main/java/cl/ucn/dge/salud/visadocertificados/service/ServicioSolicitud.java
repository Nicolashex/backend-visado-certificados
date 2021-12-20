package cl.ucn.dge.salud.visadocertificados.service;

import cl.ucn.dge.salud.visadocertificados.cuerpo_entidad.CuerpoSolicitud;
import cl.ucn.dge.salud.visadocertificados.dto.ModificarSolicitudEstudianteDto;
import cl.ucn.dge.salud.visadocertificados.model.Documento;
import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import cl.ucn.dge.salud.visadocertificados.model.User;
import cl.ucn.dge.salud.visadocertificados.projection.*;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioSolicitud;
import cl.ucn.dge.salud.visadocertificados.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private final EmailService emailService;

    public ServicioSolicitud(RepositorioSolicitud repositorioSolicitud,
                             ServicioDocumento servicioDocumento,
                             ServicioUsuario servicioUsuario, ServicioCarrera servicioCarrera, EmailService emailService) {
        this.repositorioSolicitud = repositorioSolicitud;
        this.servicioDocumento = servicioDocumento;
        this.servicioUsuario = servicioUsuario;
        this.servicioCarrera = servicioCarrera;
        this.emailService = emailService;
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

        Solicitud solicitudNueva = new Solicitud(solicitud.getRutMedicoTratante(),  solicitud.getNombreMedicoTratante(),solicitud.getFechaInicioReposo(),
                solicitud.getFechaFinReposo(), solicitud.getMotivo(), rutCarga, documentos, estudiante,
                solicitud.esCarga(), nombreCarga);

        return repositorioSolicitud.save(solicitudNueva);

    }
    public List<SolicitudResumenAdministrador> getSolicitudes(){
        //TODO: habilitar al administrador ver todas las solicitudes
        List<SolicitudResumenAdministrador> lista = this.repositorioSolicitud.getSolicitudByEstado(Solicitud.estadosPosibles.INGRESADO);
        lista.addAll(this.repositorioSolicitud.getSolicitudByEstado(Solicitud.estadosPosibles.CORREGIDO));
        return lista;
    }
    public List<Solicitud> getAllSolicitudes(){
        return this.repositorioSolicitud.findAll();
    }

    public SolicitudDetalladaAdministrador getSolicitudDetalladaAdministrador(Long id) {

        return this.repositorioSolicitud.getSolicitudDetalladaAdministrador(id);

    }
    public SolicitudDetalladaMedico getSolicitudDetalladaMedico(Long id, User medico) {

        if(this.repositorioSolicitud.existsSolicitudByIdProfesionalAndId(medico,id)){
            return this.repositorioSolicitud.getSolicitudDetalladaMedico(id);
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public SolicitudDetalladaEstudiante getSolicitudDetalladaEstudiante(Long idSolicitud, User estudiante) {

        if(this.repositorioSolicitud.existsSolicitudByEstudianteAndId(estudiante,idSolicitud)){
            return this.repositorioSolicitud.getSolicitudDetalladaEstudiante(idSolicitud);
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public List<SolicitudResumenEstudiante> getSolicitudEstudiante(Long id){
        return this.repositorioSolicitud.getSolicitudesEstudiante(id);
    }


    public List<SolicitudResumenMedico> getSolicitudesMedico(Long id) {
        return this.repositorioSolicitud.getSolicitudesPorMedicoYEstado(id,
                Solicitud.estadosPosibles.EN_EVALUACION);
    }

    @Transactional
    public void modificarSolicitudAdministrador(Long id, Long idProfesional,
                                                String comentario,
                                                Solicitud.estadosPosibles estado) throws IOException, MessagingException {

        Optional<Solicitud> existeSolicitud = repositorioSolicitud.findById(id);
        if(!existeSolicitud.isPresent()){
            throw new IOException("Solicitud no existe");
        }
        Solicitud solicitud = repositorioSolicitud.getSolicitudById(id);
        Optional<User> medicoExiste = servicioUsuario.existeMedicoPorId(idProfesional);
        User estudiante = solicitud.getEstudiante();
        switch(estado) {

            case EN_EVALUACION:
                if(!medicoExiste.isPresent()){
                    throw new IOException("No existe un medico con ese id");
                }
                solicitud.setComentario(comentario);
                User medico = servicioUsuario.getUsuarioById(idProfesional);
                solicitud.setIdProfesional(medico);

                if (!solicitud.getEstado().equals(Solicitud.estadosPosibles.EN_EVALUACION.name())){
                    solicitud.setIngresoEvaluacion(LocalDateTime.now());
                    solicitud.setEstado(Solicitud.estadosPosibles.EN_EVALUACION);
                    emailService.enviarMensaje(medico.getCorreo(),
                            "Nueva solicitud de visado asignada",
                            "Se le ha asignado la solicitud de visado con ID:"+
                            solicitud.getId());
                }
                break;
            case EN_CORRECCION:
                solicitud.setComentario(comentario);
                solicitud.setEstado(Solicitud.estadosPosibles.EN_CORRECCION);
                break;
            case RECHAZADO:
                solicitud.setComentario(comentario);
                if (!solicitud.getEstado().equals(Solicitud.estadosPosibles.RECHAZADO.name())){
                    solicitud.setFechaFinSolicitud(LocalDateTime.now());
                    solicitud.setEstado(Solicitud.estadosPosibles.RECHAZADO);
                    emailService.enviarMensaje(estudiante.getCorreo(),
                            "Solicitud de visado rechazada",
                            "Estimado estudiante " + estudiante.getNombre() +
                            ", la solicitud de visado con ID:" + solicitud.getId() +" ha sido RECHAZADA");
                }
                break;
            default:
                throw new IOException("Estado ingresado no valido");
        }
    }

    @Transactional
    public void modificarSolicitudMedico(Long id, String comentario,
                                         Solicitud.estadosPosibles estado,
                                         String correoMedico) throws IOException, MessagingException {

        User medico = this.servicioUsuario.getUsuarioPorCorreo(correoMedico);

        Optional<Solicitud> existeSolicitud = repositorioSolicitud.findById(id);
        if(!existeSolicitud.isPresent()){
            throw new IOException("Solicitud no existe");
        }else if(!repositorioSolicitud.existsSolicitudByIdProfesionalAndId(medico,id)){
            throw new IOException("Solicitud no corresponde al profesional");
        }

        Solicitud solicitud = repositorioSolicitud.getSolicitudById(id);
        solicitud.setComentario(comentario);
        solicitud.setFechaFinSolicitud(LocalDateTime.now());
        solicitud.setRespuestaEvaluacion(LocalDateTime.now());
        User estudiante = solicitud.getEstudiante();
        switch (estado){

            case RECHAZADO:
                solicitud.setEstado(Solicitud.estadosPosibles.RECHAZADO);
                emailService.enviarMensaje(estudiante.getCorreo(),
                        "Solicitud de visado rechazada",
                        "Estimado estudiante " + estudiante.getNombre() +
                                ", la solicitud de visado con ID:" + solicitud.getId() +" ha sido RECHAZADA");
                break;

            case APROBADO:
                solicitud.setEstado(Solicitud.estadosPosibles.APROBADO);
                emailService.enviarMensaje(estudiante.getCorreo(),
                        "Solicitud de visado aprobada",
                        "Estimado estudiante " + estudiante.getNombre() +
                                ", la solicitud de visado con ID:" + solicitud.getId() +" ha sido APROBADA");
                break;

            default:
                throw new IOException("Estado ingresado no valido");
        }
    }

    public void modificarSolicitudEstudiante(Long id, ModificarSolicitudEstudianteDto modificarSolicitudEstudianteDto) throws IOException {


        Optional<Solicitud> existeSolicitud = repositorioSolicitud.findById(id);
        if(!existeSolicitud.isPresent()){
            throw new IOException("Solicitud no existe");
        }

        Solicitud solicitud = repositorioSolicitud.getSolicitudById(id);

        //Solo puede editar si esta en estado EN_CORRECION
        if (solicitud.getEstado().name().equals(Solicitud.estadosPosibles.EN_CORRECCION.name())) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            if(modificarSolicitudEstudianteDto.getNombreCarga() != null) {solicitud.setNombreCarga(modificarSolicitudEstudianteDto.getNombreCarga());}
            if(modificarSolicitudEstudianteDto.getRutCarga() != null){solicitud.setRutCarga(modificarSolicitudEstudianteDto.getRutCarga());}
            if(modificarSolicitudEstudianteDto.getDiagnostico() != null){solicitud.setMotivo(modificarSolicitudEstudianteDto.getDiagnostico());}
            if(modificarSolicitudEstudianteDto.getNombreMedicoTratante() != null){  solicitud.setNombreMedicoTratante(modificarSolicitudEstudianteDto.getNombreMedicoTratante());}
            if(modificarSolicitudEstudianteDto.getRutMedicoTratante() != null){  solicitud.setRutMedicoTratante(modificarSolicitudEstudianteDto.getRutMedicoTratante());}
            if(modificarSolicitudEstudianteDto.getFechaInicioReposo() != null){
                solicitud.setFechaInicioReposo(LocalDate.parse(modificarSolicitudEstudianteDto.getFechaInicioReposo(),
                    formatter));
            }
            if(modificarSolicitudEstudianteDto.getFechaFinReposo() != null){
            solicitud.setFechaFinReposo(LocalDate.parse(modificarSolicitudEstudianteDto.getFechaFinReposo(),
                    formatter));
            }
        }else{
            throw new IOException("Estado no permite modificaciones");
        }



    }

    public List<Documento> getDocumentoById(Long id){

        Solicitud solicitud = this.repositorioSolicitud.getSolicitudById(id);
        return solicitud.getDocumentos();
    }

    public Solicitud getSolicitudById(Long id){
        return repositorioSolicitud.getSolicitudById(id);
    }
}
