package cl.ucn.dge.salud.visadocertificados.controller;

import cl.ucn.dge.salud.visadocertificados.cuerpo_entidad.CuerpoSolicitud;
import cl.ucn.dge.salud.visadocertificados.dto.ModificarSolicitudAdministradorDto;
import cl.ucn.dge.salud.visadocertificados.dto.ModificarSolicitudEstudianteDto;
import cl.ucn.dge.salud.visadocertificados.dto.ModificarSolicitudMedicoDto;
import cl.ucn.dge.salud.visadocertificados.model.Documento;
import cl.ucn.dge.salud.visadocertificados.model.Rol;
import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import cl.ucn.dge.salud.visadocertificados.model.User;
import cl.ucn.dge.salud.visadocertificados.projection.*;
import cl.ucn.dge.salud.visadocertificados.service.ServicioDocumento;
import cl.ucn.dge.salud.visadocertificados.service.ServicioSolicitud;
import cl.ucn.dge.salud.visadocertificados.service.ServicioUsuario;
import cl.ucn.dge.salud.visadocertificados.service.email.EmailService;
import cl.ucn.dge.salud.visadocertificados.utils.SolicitudesExcelExport;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/v1")
public class ControladorRestSolicitud {

    @Autowired
    private final ServicioSolicitud servicioSolicitud;

    @Autowired
    private final ServicioUsuario servicioUsuario;

    @Autowired
    private final ServicioDocumento servicioDocumento;

    @Autowired
    private final EmailService emailService;


    public ControladorRestSolicitud(ServicioSolicitud servicioSolicitud,
                                    ServicioUsuario servicioUsuario,
                                    ServicioDocumento servicioDocumento,
                                    EmailService emailService) {
        this.servicioSolicitud = servicioSolicitud;
        this.servicioUsuario = servicioUsuario;
        this.servicioDocumento = servicioDocumento;
        this.emailService = emailService;
    }

    @PostMapping(value = "/solicitudes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<String> registrarSolicitud(@Validated @RequestPart CuerpoSolicitud solicitud,
                                                    @RequestParam("certificado") MultipartFile[] certificado,
                                                    @RequestParam("respaldo") MultipartFile[] respaldo) throws JsonProcessingException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String mensaje = "";
        String correoUsuario = (String) auth.getPrincipal();
        try {
            servicioSolicitud.ingresarSolicitud(solicitud, correoUsuario, certificado, respaldo);
            mensaje = "Solicitud creada de forma exitosa";
            return ResponseEntity.status(HttpStatus.OK).body(mensaje);

        } catch (InternalError e) {
            mensaje = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje);

        } catch (IOException e) {
            mensaje = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(mensaje);
        } catch (MessagingException e) {
            mensaje = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje);
        }

    }
    @GetMapping(value = "/admin/solicitudes")
    public List<SolicitudResumenAdministrador> getSolicitudes(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(Rol.enumRole.ROL_ADMINISTRADOR.name()));
        if(hasUserRole) {
            return this.servicioSolicitud.getSolicitudes();
        }

        return null;

    }

    @GetMapping(value ="/medico/solicitudes/{id}")
    public SolicitudDetalladaMedico getSolicitudDetalladaMedico(@PathVariable Long id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(Rol.enumRole.ROL_MEDICO.name()));
        if(hasUserRole){
            String correo = (String) authentication.getPrincipal();
            User medico = this.servicioUsuario.getUsuarioPorCorreo(correo);
            return this.servicioSolicitud.getSolicitudDetalladaMedico(id,medico);
        }
        return  null;

    }


    @GetMapping(value = "/solicitudes")
    public List<Solicitud> getAllSolicitudes(){
        return this.servicioSolicitud.getAllSolicitudes();
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return errors;
    }
    @GetMapping("/admin/solicitudes/{id}")
    public SolicitudDetalladaAdministrador getSolicitudDetalladaAdministrador(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(Rol.enumRole.ROL_ADMINISTRADOR.name()));
        if(hasUserRole) {
            return this.servicioSolicitud.getSolicitudDetalladaAdministrador(id);
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/estudiante/solicitudes/{id}")
    public SolicitudDetalladaEstudiante getSolicitudDetalladaEstudiante(@PathVariable Long id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(Rol.enumRole.ROL_ESTUDIANTE.name()));
        if(hasUserRole){
            User estudiante = servicioUsuario.getUsuarioPorCorreo((String) authentication.getPrincipal());
            return this.servicioSolicitud.getSolicitudDetalladaEstudiante(id,estudiante);
        }
        return  null;

    }
    @GetMapping(value ="/medico/solicitudes/")
    public List<SolicitudResumenMedico> getSolicitudResumidaMedico(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(Rol.enumRole.ROL_MEDICO.name()));
        if(hasUserRole){
            String correo = (String) authentication.getPrincipal();
            User medico = servicioUsuario.getUsuarioPorCorreo(correo);
            return this.servicioSolicitud.getSolicitudesMedico(medico.getId());
        }
        return null;
    }

    @GetMapping("/estudiante/solicitudes")
    public List<SolicitudResumenEstudiante> getSolicitudesEstudiante(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(Rol.enumRole.ROL_ESTUDIANTE.name()));

        if(hasUserRole){
            String correo = (String) authentication.getPrincipal();
            User user = servicioUsuario.getUsuarioPorCorreo(correo);

            return servicioSolicitud.getSolicitudEstudiante(user.getId());
        }

        return null;
    }

    @GetMapping("/reportes/solicitudes")
    public void exportExcel(HttpServletResponse response) throws IOException{

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=solicitudes_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Solicitud> listaSolicitudes = servicioSolicitud.getAllSolicitudes();

        SolicitudesExcelExport excelExport = new SolicitudesExcelExport(listaSolicitudes);

        excelExport.export(response);

    }

    @PostMapping("/admin/solicitudes/{id}")
    public ResponseEntity<String> updateSolicitudAdministrador(@PathVariable Long id,
                                                          @RequestBody ModificarSolicitudAdministradorDto cambios) {
        String mensaje = "";

        /**
         * NOTA IMPORTANTE
         * SIEMPRE DEBE ENVIAR EL ESTADO, DEBE SER DIFERENTE DE NULLs
         */
        try {
            servicioSolicitud.modificarSolicitudAdministrador(id, cambios.getIdProfesional(),
                    cambios.getComentario(),
                    cambios.getEstado());
            mensaje = "Solicitud modificada de forma exitosa";
            return ResponseEntity.status(HttpStatus.OK).body(mensaje);
        } catch (Exception e) {
            mensaje = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje);
        }
    }

    @PostMapping("/medico/solicitudes/{id}")
    public ResponseEntity<String> updateSolicitudMedico(@PathVariable Long id,
                                                        @RequestBody ModificarSolicitudMedicoDto cambios){
        String mensaje = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoMedico = (String) authentication.getPrincipal();

        try {
            servicioSolicitud.modificarSolicitudMedico(
                    id, //id solicitud
                    cambios.getComentario(),
                    cambios.getEstado(), //no null
                    correoMedico);
            mensaje = "Solicitud modificada de forma exitosa";
            return ResponseEntity.status(HttpStatus.OK).body(mensaje);
        } catch (Exception e) {
            mensaje = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje);
        }
    }

    @Transactional
    @PostMapping(value ="/estudiante/solicitudes/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<String> updateSolicitudEstudiante(@PathVariable Long id,
                                                            @RequestPart ModificarSolicitudEstudianteDto cambios,
                                                            @RequestParam("certificado") MultipartFile[] certificado,
                                                            @RequestParam("respaldo") MultipartFile[] respaldo) throws IOException {

        String mensaje = "";
        try {
            //Primero evaluar si es posible modificar el form
            servicioSolicitud.modificarSolicitudEstudiante(
                    id,
                    cambios
            );

            //Evaluar documentos
            Solicitud solicitud = this.servicioSolicitud.getSolicitudById(id);

            //Eliminar los archivos que no esten en la lista cambios.getListaId
            List<String> listaId = cambios.getListaIdDocumento();

            //Cargar los documentos para la solicitud
            List<Documento> listDocumentos = this.servicioSolicitud.getDocumentoById(id);

            //lista auxiliar para eliminar
            List<String> eliminados = new ArrayList<>();

            if (listaId != null) {
                for (Documento documento : listDocumentos) {
                    String idDocumento = documento.getId();
                    if (listaId.contains(idDocumento)) {
                        eliminados.add(idDocumento);
                    }
                }
                for (String documentoEliminado : eliminados) {
                    Documento documento = servicioDocumento.getDocumentoPorId(documentoEliminado);
                    solicitud.eliminarDocumento(documento);
                }
            }

            List<Documento> lista = this.servicioDocumento.guardarDocumento(certificado, respaldo);
            solicitud.agregarDocumentos(lista);

            //Cambio de estado a CORREGIDO
            solicitud.setEstado(Solicitud.estadosPosibles.CORREGIDO);

            mensaje = "Solicitud modificada de forma exitosa";
            return ResponseEntity.status(HttpStatus.OK).body(mensaje);

        }catch (Exception e){
            mensaje = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje);
        }
    }
    @GetMapping("/xd")
    public void testEmail() throws MessagingException {
        emailService.enviarMensaje("nicolashex1@gmail.com","Prueba de email", "muyy buenas que tal todo");

    }

}
