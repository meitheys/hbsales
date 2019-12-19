package br.com.hbsis.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Email {

    @Autowired private JavaMailSender mailSender;

    @RequestMapping(path = "/email-send", method = RequestMethod.GET)
    public String enviarEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Hello");
        message.setText("<p>Hello from Spring Boot Application</p>");
        message.setTo("matheus.furtado@hbsis.com.br");
        message.setFrom("math.furtadonn1ptv@gmail.com");

        try {
            mailSender.send(message);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }
    }

}
