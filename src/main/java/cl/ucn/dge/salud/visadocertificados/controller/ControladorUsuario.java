package cl.ucn.dge.salud.visadocertificados.controller;

import cl.ucn.dge.salud.visadocertificados.dto.RegistroUserDto;
import cl.ucn.dge.salud.visadocertificados.model.User;
import cl.ucn.dge.salud.visadocertificados.service.ServicioUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registro")
public class ControladorUsuario {

    private ServicioUser servicioUser;

    @Autowired
    public ControladorUsuario(ServicioUser servicioUser) {
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
    public String registroUsuario(@ModelAttribute("usuario") RegistroUserDto registroUserDto, RedirectAttributes redirectAttributes){

        if(servicioUser.rutDisponible(registroUserDto.getRut())){
            redirectAttributes.addFlashAttribute("error", "Rut de usuario ya está en uso, intenta nuevamente");
            return "redirect:/registro?error_rut";
        }else if(servicioUser.correoDisponible(registroUserDto.getCorreo())){
            redirectAttributes.addFlashAttribute("error", "Correo de usuario ya está en uso, intenta nuevamente");
            return "redirect:/registro?error_correo";
        }else{
            servicioUser.save(registroUserDto);
            return "redirect:/registro?success";
        }
    }


}
