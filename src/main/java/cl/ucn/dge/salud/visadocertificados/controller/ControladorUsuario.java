package cl.ucn.dge.salud.visadocertificados.controller;

import cl.ucn.dge.salud.visadocertificados.dto.RegistroUserDto;

import cl.ucn.dge.salud.visadocertificados.excepcion.ApiError;
import cl.ucn.dge.salud.visadocertificados.excepcion.CredencialesRegistradasException;
import cl.ucn.dge.salud.visadocertificados.model.User;
import cl.ucn.dge.salud.visadocertificados.projection.MedicoResumen;
import cl.ucn.dge.salud.visadocertificados.service.ServicioUsuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("")
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

    @PostMapping("/registro")
    public User registroUsuario(@RequestBody RegistroUserDto registroUserDto) {

        String mensaje;
        //TODO: validar carrera string
        if(servicioUser.rutDisponible(registroUserDto.getRut())){
            mensaje = "Error, rut ya se encuentra registrado";
            throw new CredencialesRegistradasException(mensaje);

        }
        if(servicioUser.correoDisponible(registroUserDto.getCorreo())){
            mensaje = "Error, correo ya se encuentra registrado";
            throw new CredencialesRegistradasException(mensaje);
        }
        return servicioUser.save(registroUserDto);


    }
    @GetMapping("/usuarios/medicos")
    public List<MedicoResumen> getMedicos(){
        return this.servicioUser.getMedicosResumen();
    }

    @ExceptionHandler(CredencialesRegistradasException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError manejarCredencialesRegistradasException(CredencialesRegistradasException exception,
                                                            HttpServletRequest request) {
        ApiError error = new ApiError(409, exception.getMessage(), request.getServletPath());
        return  error;
    }



}
