package br.com.edson.cursoSpring.resources.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.edson.cursoSpring.exceptions.AmazonException;
import br.com.edson.cursoSpring.exceptions.AuthorizationException;
import br.com.edson.cursoSpring.exceptions.CPF_Ou_CNPJUtilizado;
import br.com.edson.cursoSpring.exceptions.EmailUtilizado;
import br.com.edson.cursoSpring.exceptions.FileException;
import br.com.edson.cursoSpring.exceptions.IntegridadeViolada;
import br.com.edson.cursoSpring.exceptions.ObjectNotFound;

@ControllerAdvice
public class ResourceExceptionhandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EmailUtilizado.class)
	public ResponseEntity<?> emailUsado( EmailUtilizado emailUsado, HttpServletRequest request) {
		return genericErrorHandler(emailUsado, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}
	
	@ExceptionHandler(CPF_Ou_CNPJUtilizado.class)
	public ResponseEntity<?> cpfOUCNPJUsado( CPF_Ou_CNPJUtilizado docUsado, HttpServletRequest request) {
		return genericErrorHandler(docUsado, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}
	
	
	@ExceptionHandler(ObjectNotFound.class)
	public ResponseEntity<?> objectNotFound( ObjectNotFound obj, HttpServletRequest request) {
		return genericErrorHandler(obj, HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(IntegridadeViolada.class)
	public ResponseEntity<?> integridadeViolada( IntegridadeViolada obj, HttpServletRequest request) {
		return genericErrorHandler(obj, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<?>  authorization( AuthorizationException ex, HttpServletRequest request) {
		return genericErrorHandler(ex, HttpStatus.FORBIDDEN, request);
	}
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<?> FileException( FileException ex, HttpServletRequest request) {
		return genericErrorHandler(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@ExceptionHandler(AmazonException.class)
	public ResponseEntity<?>  amazonException( AmazonException ex, HttpServletRequest request) {
		return genericErrorHandler(ex, (ex.getStatus() != null) ? ex.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	
	private ResponseEntity<?> genericErrorHandler(Exception ex, HttpStatus status, HttpServletRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");	
		
		StandardError error = new StandardError(formatter.format(LocalDateTime.now()), status.value(),
				status.getReasonPhrase(), ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(status).body(error);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		status = HttpStatus.UNPROCESSABLE_ENTITY;
		String message = this.getMessage(ex);
		String path = request.getDescription(false);
		String uri[] = path.split("=");
		
		ValidationError erro = new ValidationError(formatter.format(LocalDateTime.now()), status.value(),
				"Um ou mais campos inválidos.", message, uri[1] );
		headers.getOrigin();

		ArrayList<CampoComErro> campos = new ArrayList<CampoComErro>();
		
		for ( ObjectError error : ex.getBindingResult().getAllErrors()) {
			String campo = ( (FieldError)error ).getField();
			String msg = error.getDefaultMessage();
			campos.add(new CampoComErro(campo, msg));
		}
		
		erro.getCamposComErro().addAll(campos);
		
		return handleExceptionInternal(ex, erro, headers, status, request);
	}
	

	private String getMessage(MethodArgumentNotValidException ex) {
		
		StringBuilder sb =  new StringBuilder(
				"O request tem " + ex.getErrorCount() + " erros.");
		return sb.toString();
	}
	
}
