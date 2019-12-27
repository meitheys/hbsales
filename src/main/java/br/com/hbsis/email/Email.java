package br.com.hbsis.email;

import br.com.hbsis.item.Item;
import br.com.hbsis.pedido.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Component
public class Email {
    @Autowired
    private JavaMailSender mailSender;
    private Item item;

    public void enviar(Pedido pedido, List listaDeitens) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("Compra feita! =)");
        message.setText("Bom dia caro " + pedido.getFuncionario().getNomeFuncionario() + "\r\n"
                + " Você comprou " + item.getProduto() + " e a data de retirada será em " + pedido.getIdPeriodo().getRetirada()
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

    public String enviarAtualizacao(Pedido pedido) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Pedido Atualizado! =)");
        message.setText("Bom dia caro " + pedido.getFuncionario().getNomeFuncionario() + "\r\n"
                + " Seu pedido " + pedido.getId() + " foi atualizado, gostariamos de lembrar que a sua data de retirada será em " + pedido.getIdPeriodo().getRetirada()
                + "\r\n"
                + "\r\n"
                + "HBSIS - Soluções em TI" + "\r\n"
                + "Rua Theodoro Holtrup, 982 - Vila Nova, Blumenau - SC"
                + "(47) 2123-5400");

        message.setTo(pedido.getFuncionario().getEmail());
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
