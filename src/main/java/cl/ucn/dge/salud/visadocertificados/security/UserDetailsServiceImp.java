package cl.ucn.dge.salud.visadocertificados.security;


import cl.ucn.dge.salud.visadocertificados.model.Rol;
import cl.ucn.dge.salud.visadocertificados.model.User;
import cl.ucn.dge.salud.visadocertificados.service.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImp implements UserDetailsService {

    /**
     * the service that allow to make request to the database.
     */
    @Autowired
    private ServicioUsuario userService;

    @Override
    public UserDetails loadUserByUsername(final String email)
            throws UsernameNotFoundException {
        try {
            final Optional<User> user = userService.findUsersByEmail(email);
            if (!user.isPresent()) {
                throw new UsernameNotFoundException(
                        "No se encuentra usuario con mail: " + email);
            }
            return new org.springframework.security.core.userdetails.User(
                    user.get().getCorreo(), user.get().getContrasena(), true,
                    true, true, true,
                    getAuthorities(user.get().getRoles())
            );
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            final Rol role) {
        return getGrantedAuthorities(getPrivileges(role));
    }

    private List<String> getPrivileges(final Rol role) {
        List<String> privileges = new ArrayList<>();
        privileges.add(role.getName().name());
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(
            final List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}