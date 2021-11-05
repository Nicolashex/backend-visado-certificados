package cl.ucn.dge.salud.visadocertificados.service;

import cl.ucn.dge.salud.visadocertificados.dto.RegistroUserDto;
import cl.ucn.dge.salud.visadocertificados.model.Rol;
import cl.ucn.dge.salud.visadocertificados.model.User;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ServicioUserImpl implements ServicioUser{

    @Autowired
    private RepositorioUser repositorioUser;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ServicioUserImpl(RepositorioUser repositorioUser){
        super();
        this.repositorioUser = repositorioUser;
    }

    @Override
    public User save(RegistroUserDto registroUserDto) {

        User user = new User(
                registroUserDto.getCorreo(),
                registroUserDto.getRut(),
                passwordEncoder.encode(registroUserDto.getContrasena()), //Cifrar la contraseña
                registroUserDto.getNombre(),
                registroUserDto.getPrimerApellido(),
                registroUserDto.getSegundoApellido(),
                registroUserDto.getTelefono(),
                registroUserDto.getCarrera(),
                registroUserDto.getCargo(),
                registroUserDto.getProfesion(),
                Arrays.asList(new Rol("Estudiante")));

        return repositorioUser.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = repositorioUser.buscarPorCorreo(s);

        if(user == null){
            throw new UsernameNotFoundException("Correo o contraseña incorrectos");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getCorreo(),
                user.getContrasena(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    /**
     * Reglas segun rol
     * @return
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Rol> roles){
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getNombreRol())).collect(Collectors.toList());
    }


}
