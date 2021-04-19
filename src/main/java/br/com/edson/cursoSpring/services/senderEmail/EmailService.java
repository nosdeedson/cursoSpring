package br.com.edson.cursoSpring.services.senderEmail;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.edson.cursoSpring.model.Cliente;
import br.com.edson.cursoSpring.model.Pedido;

public interface EmailService {

	void enviarOrdemConfirmacaoEmail( Pedido pedido);
	
	void sendEmail( SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);
	
	void sendNewPassWordEmail( String novaSenha);
}
