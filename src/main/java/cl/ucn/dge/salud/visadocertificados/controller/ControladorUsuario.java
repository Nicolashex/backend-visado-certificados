package cl.ucn.dge.salud.visadocertificados.controller;

import cl.ucn.dge.salud.visadocertificados.dto.RegistroUserDto;

import cl.ucn.dge.salud.visadocertificados.service.ServicioUsuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RestController
@RequestMapping("/registro")
public class ControladorUsuario {

    private ServicioUsuario servicioUser;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUser) {
        super();
        this.servicioUser = servicioUser;
    }

    @ModelAttribute("usuario")
    public RegistroUserDto registroUserDto(){
        return new RegistroUserDto();
    }

    @GetMapping()
    public String mostrarRegistro(){
        return "registro";
    }

    @PostMapping
    public ResponseEntity<String> registroUsuario(@RequestBody RegistroUserDto registroUserDto)  throws JsonProcessingException, IOException {

        String mensaje;

        try{
            if(servicioUser.rutDisponible(registroUserDto.getRut())){
                mensaje = "Error, rut ya se encuentra registrado";
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(mensaje) ;
            }else if(servicioUser.correoDisponible(registroUserDto.getCorreo())){
                mensaje = "Error, correo ya se encuentra registrado";
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(mensaje) ;
            }else{
                servicioUser.save(registroUserDto);
                mensaje = "Usuario creado de forma exitosa";
                return ResponseEntity.status(HttpStatus.OK).body(mensaje);
            }
        } catch (InternalError e) {
            mensaje = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje);
        }
    }


}
