package cl.ucn.dge.salud.visadocertificados.service.email;

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

}
