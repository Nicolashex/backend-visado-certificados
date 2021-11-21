package cl.ucn.dge.salud.visadocertificados.validacion;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorRut.class)
public @interface ValidacionRut {
    /**
     * Returna un mensaje por defecto cuando rut no es valido
     *
     * @return un mensaje por defecto.
     */
    String message() default "Rut is not valid";

    Class<?>[] groups() default {};

    /**
     * Returna un array de Payloads con la metadata.
     *
     * @return Un array de Payloads.
     */
    Class<? extends Payload>[] payload() default {};
}
