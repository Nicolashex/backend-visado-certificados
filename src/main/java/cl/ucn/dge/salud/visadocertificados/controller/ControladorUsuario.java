package cl.ucn.dge.salud.visadocertificados.controller;

import cl.ucn.dge.salud.visadocertificados.dto.RegistroUserDto;

import cl.ucn.dge.salud.visadocertificados.service.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public void registroUsuario(@RequestBody   RegistroUserDto registroUserDto){

        if(servicioUser.rutDisponible(registroUserDto.getRut())){

            return ;
        }else if(servicioUser.correoDisponible(registroUserDto.getCorreo())){

            return ;
        }else{
            servicioUser.save(registroUserDto);

        }
    }


}
