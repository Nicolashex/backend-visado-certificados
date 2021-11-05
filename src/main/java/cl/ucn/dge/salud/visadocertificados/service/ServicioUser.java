package cl.ucn.dge.salud.visadocertificados.service;

import cl.ucn.dge.salud.visadocertificados.dto.RegistroUserDto;
import cl.ucn.dge.salud.visadocertificados.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ServicioUser extends UserDetailsService {

    User save(RegistroUserDto registroUserDto);

    boolean correoDisponible(String correo);

    boolean rutDisponible(String rut);

}
