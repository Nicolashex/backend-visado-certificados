package cl.ucn.dge.salud.visadocertificados.service.email;

import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import cl.ucn.dge.salud.visadocertificados.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    private final EmailConfig emailConfig;

    public EmailService(JavaMailSender emailSender, EmailConfig emailConfig) {
        this.emailSender = emailSender;
        this.emailConfig = emailConfig;
    }

    public void enviarMensaje(
            String destinatario, String asunto, String texto) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        //helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
        //String inlineImage = "<img src=\"cid:logoImage\"></img><br/>";
        String inlineImage = "<hr><img src='cid:logoImage' />";
        helper.setText(  texto + inlineImage, true);
        ClassPathResource resource = new ClassPathResource(emailConfig.getPathLogo());
        helper.addInline("logoImage", resource);
        helper.setFrom("noreply@dge.cl");
        helper.setTo(destinatario);
        helper.setSubject(asunto);
        emailSender.send(message);
    }
    public void notificarEstudianteIngresoSolicitud(User estudiante, Solicitud solicitud) throws MessagingException {

        String mensaje = "Estimad@ " + estudiante.getNombre() + ": \n" +
                "La solicitud de visado con ID: " + solicitud.getId() +
                " fue ingresada correctamente, en el caso de que necesite correciones el equipo de " +
                "administraci??n se contactar?? contigo de lo contrario un profesional evaluador revisar??" +
                "tu solicitud.";
        String asunto = "Actualizaci??n de solicitud de visado";
        enviarMensaje(estudiante.getCorreo(), asunto, mensaje);
    }
    public void notificarEstudianteCorrecionSolicitud(User estudiante, Solicitud solicitud) throws MessagingException {

        String mensaje = "Estimad@ " + estudiante.getNombre() + ":\n" +
                "La solicitud de visado con ID: " + solicitud.getId() +
                " necesita ser corregida. Para editarla dir??gete a la secci??n: Menu principal ->" +
                " Ver solicitudes -> Modificar.";
        String asunto = "Actualizaci??n de solicitud de visado";
        enviarMensaje(estudiante.getCorreo(), asunto, mensaje);
    }
    public void notificarAsignacionDeSolicitudMedico(User medico, Solicitud solicitud) throws MessagingException {

        String mensaje = "Estimad@ " + medico.getNombre() + ":\n" +
                "Se le ha asignado la solicitud de visado con ID: " + solicitud.getId() +
                " para su pronta correcci??n.";
        String asunto = "Asignacion de solicitud de visado.";
        enviarMensaje(medico.getCorreo(), asunto, mensaje);
    }

    public void notificarEstudianteAprobacionSolicitud(User estudiante, Solicitud solicitud) throws MessagingException {

        String mensaje = "Estimad@ " + estudiante.getNombre() + ": \n" +
                "La solicitud de visado con ID: " + solicitud.getId() +
                "ha sido APROBADA. Recuerda que para visualizarla debes dirigirte al apartado: Menu principal -> " +
                "Ver solicitudes y presionar el bot??n VER de la solicitud correspondiente \n" +
                "Recuerda que para visualizar el documento visado debes responder la evaluaci??n del servicio antes.";
        String asunto = "Solicitud de visado aprobada.";
        enviarMensaje(estudiante.getCorreo(), asunto, mensaje);
    }
    public void notificarEstudianteRechazoSolicitud(User estudiante, Solicitud solicitud) throws MessagingException {

        String mensaje = "Estimad@ " + estudiante.getNombre() + ": \n" +
                "Tu solicitud de visado N??" + solicitud.getId() +
                " ha sido RECHAZADA.";
        String asunto = "Solicitud de visado rechazada.";
        enviarMensaje(estudiante.getCorreo(), asunto, mensaje);
    }

}
