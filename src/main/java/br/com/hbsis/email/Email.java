package br.com.hbsis.email;

import br.com.hbsis.pedido.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Email {
    @Autowired
    private JavaMailSender mailSender;

    public void enviar(Pedido pedido) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("Compra feita! =)");
        message.setText("Bom dia caro " + pedido.getFuncionario().getNomeFuncionario() + "\r\n"
                + " Você comprou " + pedido.getProduto().getNomeProduto() + " e a data de retirada será em " + pedido.getIdPeriodo().getRetirada()
                + "\r\n"
                + "\r\n"
                + "HBSIS - Soluções em TI" + "\r\n"
                + "Rua Theodoro Holtrup, 982 - Vila Nova, Blumenau - SC"
                + "(47) 2123-5400");
        message.setTo(pedido.getFuncionario().getEmail());
        message.setFrom("math.furtadonn1ptv@gmail.com");
        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
