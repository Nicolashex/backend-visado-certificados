package cl.ucn.dge.salud.visadocertificados.controller;

import cl.ucn.dge.salud.visadocertificados.cuerpo_entidad.CuerpoSolicitud;
import cl.ucn.dge.salud.visadocertificados.model.Rol;
import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import cl.ucn.dge.salud.visadocertificados.model.User;
import cl.ucn.dge.salud.visadocertificados.projection.*;
import cl.ucn.dge.salud.visadocertificados.service.ServicioSolicitud;
import cl.ucn.dge.salud.visadocertificados.service.ServicioUsuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class ControladorRestSolicitud {

    @Autowired
    private final ServicioSolicitud servicioSolicitud;

    @Autowired
    private final ServicioUsuario servicioUsuario;



    public ControladorRestSolicitud(ServicioSolicitud servicioSolicitud,
                                    ServicioUsuario servicioUsuario) {
        this.servicioSolicitud = servicioSolicitud;
        this.servicioUsuario = servicioUsuario;
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
        }

    }
    @GetMapping(value = "/admin/solicitudes")
    public List<SolicitudResumenAdministrador> getSolicitudes(){
        return this.servicioSolicitud.getSolicitudes();
    }

    @GetMapping(value ="/medico/solicitudes/{id}")
    public SolicitudDetalladaMedico getSolicitudDetalladaMedico(@PathVariable Long id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(Rol.enumRole.ROL_MEDICO.name()));
        if(hasUserRole){
            String correo = (String) authentication.getPrincipal();
            User medico = this.servicioUsuario.getUsuarioPorCorreo(correo);
            return this.servicioSolicitud.getSolicitudDetalladaMedico(medico.getId());
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
        return this.servicioSolicitud.getSolicitudDetalladaAdministrador(id);
    }

    @GetMapping("/estudiante/solicitudes/{id}")
    public SolicitudDetalladaEstudiante getSolicitudDetalladaEstudiante(@PathVariable Long id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(Rol.enumRole.ROL_ESTUDIANTE.name()));
        if(hasUserRole){
            return this.servicioSolicitud.getSolicitudDetalladaEstudiante(id);
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
}
