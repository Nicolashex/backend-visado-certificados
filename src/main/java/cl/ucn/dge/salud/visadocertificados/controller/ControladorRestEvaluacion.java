package cl.ucn.dge.salud.visadocertificados.controller;


import cl.ucn.dge.salud.visadocertificados.cuerpo_entidad.CuerpoValoracion;
import cl.ucn.dge.salud.visadocertificados.service.ServicioUsuario;
import cl.ucn.dge.salud.visadocertificados.service.ServicioValoracion;
import cl.ucn.dge.salud.visadocertificados.validacion.ValidacionRut;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1")
public class ControladorRestEvaluacion {

    @Autowired
    private final ServicioValoracion servicioValoracion;

    @Autowired
    private final ServicioUsuario servicioUsuario;

    public ControladorRestEvaluacion(ServicioValoracion servicioValoracion, ServicioUsuario servicioUsuario) {
        this.servicioValoracion = servicioValoracion;
        this.servicioUsuario = servicioUsuario;
    }

    @PostMapping(value="/estudiante/valoracion")
    public ResponseEntity<String> registrarEvaluacion(@RequestBody CuerpoValoracion valoracion) throws JsonProcessingException {

        String mensaje;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuario = (String) auth.getPrincipal();

        try{
            servicioValoracion.registrarValoracion(valoracion);
            mensaje = "Valoración registrada de forma exitosa";
            return ResponseEntity.status(HttpStatus.OK).body(mensaje);
        } catch (InternalError e) {
            mensaje = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje);
        } catch (IOException e){
            mensaje = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(mensaje);
        }

    }

    @GetMapping(value="/estudiante/valoracion/check")
    public ResponseEntity<String> hasValoracion (@RequestParam Long idSolicitud){

        String mensaje;

        try{
            mensaje = servicioValoracion.hasValidacion(idSolicitud) == true ? "Solicitud valorada" : "Solicitud no valorada";
            return ResponseEntity.status(HttpStatus.OK).body(mensaje);
        } catch (InternalError e) {
            mensaje = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje);
        }
    }

}
