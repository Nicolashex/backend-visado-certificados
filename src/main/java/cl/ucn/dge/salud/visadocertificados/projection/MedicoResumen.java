package cl.ucn.dge.salud.visadocertificados.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

public interface MedicoResumen {

    Long getId();
    @JsonProperty("nombre_medico")
    @Value("#{target.nombre + ' ' + target.primerApellido+ ' '+ target.segundoApellido}")
    String getNombreMedico();

}
