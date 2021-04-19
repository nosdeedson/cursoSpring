package br.com.edson.cursoSpring.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.edson.cursoSpring.model.dto.CadastroClienteDTO;
import br.com.edson.cursoSpring.model.enums.TipoCliente;
import br.com.edson.cursoSpring.repositories.ClienteRepository;
import br.com.edson.cursoSpring.resources.exception.CampoComErro;
import br.com.edson.cursoSpring.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, CadastroClienteDTO> {

	@Autowired
	ClienteRepository clienteRepository;
	
	
	@Override
	public boolean isValid(CadastroClienteDTO objDto, ConstraintValidatorContext context) {
		
		List<CampoComErro> camposErrados = new ArrayList<>();
		
		if( TipoCliente.PESSOAFISICA.equals(objDto.getTipo()) ) {

			if ( clienteRepository.existsByCpfOuCnpj(objDto.getCpfOuCnpj())) {
				camposErrados.add(new CampoComErro("cpfOuCnpj", "CPF Já usado."));
			}
			
			if ( !BR.isValidCPF(objDto.getCpfOuCnpj())) {
				camposErrados.add(new CampoComErro("cpfOuCnpj", "CPF inválido."));
			}
			
			if( clienteRepository.existsByEmail(objDto.getEmail())) {
				camposErrados.add(new CampoComErro("Email", "Emailjá usado."));
			}
		}
		
		if ( TipoCliente.PESSOAJURIDICA.equals(objDto.getTipo()) ) {
			
			if ( clienteRepository.existsByCpfOuCnpj(objDto.getCpfOuCnpj())) {
				camposErrados.add(new CampoComErro("cpfOuCnpj", "CNPJ Já usado."));
			}
			
			if ( !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
				camposErrados.add(new CampoComErro("cpfOuCnpj", "CNPJ inválido."));
			}
			if( clienteRepository.existsByEmail(objDto.getEmail())) {
				camposErrados.add(new CampoComErro("Email", "Email já usado."));
			}
		}

		
		for ( CampoComErro erro : camposErrados) {
			
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(erro.getMessage())
			.addPropertyNode(erro.getCampo()).addConstraintViolation();
		}
		return camposErrados.isEmpty();
	}

}
