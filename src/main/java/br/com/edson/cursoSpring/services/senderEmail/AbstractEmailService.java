package br.com.edson.cursoSpring.services.senderEmail;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.edson.cursoSpring.model.Pedido;


@Service
public class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private MailSender enviaEmail;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractEmailService.class);

	@Override
	public void enviarOrdemConfirmacaoEmail(Pedido pedido) {
		SimpleMailMessage sm = preparaEmail(pedido);
		sendEmail(sm);
	}

	private SimpleMailMessage preparaEmail(Pedido pedido) {
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo("d340b12015-cf1cda@inbox.mailtrap.io");
		sm.setFrom(sender);
		sm.setSubject("Confirmação de pedido " + pedido.getId());
		sm.setText(pedido.toString());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		return sm;
	}

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("\n enviando email");
		LOG.info(msg.toString());
		LOG.info("\nemail enviado");
		enviaEmail.send(msg);
	}

	/**
	 * email html
	 */
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		MimeMessage mimeMessage;
		try {
			mimeMessage = prepareMimeMessageFromPedido(obj);
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			this.enviarOrdemConfirmacaoEmail(obj);
		}
		
		
	}

	private MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage);
		mmh.setTo("d340b12015-cf1cda@inbox.mailtrap.io");
		mmh.setFrom(sender);
		try {
			mmh.setText(this.htmlFromTemplatePedido(obj), true);
		} catch(Exception e) {
			mmh.setText("Seu pedido foi realizado com sucesso, mas devido a falhas no preparo do email, "
					+ "não foi possível enviar os dados do pedido");
		}finally {
			mmh.setSubject("Confirmação de pedido de código: " + obj.getId());
			mmh.setSentDate(new Date(System.currentTimeMillis()));
		}
		return mimeMessage;
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		// TODO Auto-generated method stub
		
	}
	
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		String template = "templates/email/confirmacaoPedido";
		return templateEngine.process(template, context);
	}

	@Override
	public void sendNewPassWordEmail(String novaSenha) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo("d340b12015-cf1cda@inbox.mailtrap.io");
		sm.setFrom(sender);
		sm.setText("Sua nova senha válida por 15 munutos é: " + novaSenha);
		sm.setSubject("Sua nova senha.");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sendEmail(sm);
	}


}
