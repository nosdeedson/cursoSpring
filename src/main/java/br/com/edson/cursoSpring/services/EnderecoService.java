package br.com.edson.cursoSpring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.edson.cursoSpring.model.dto.EnderecoDTO;
import br.com.edson.cursoSpring.repositories.EnderecoRepository;
import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class EnderecoService {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public List<EnderecoDTO> findAllEnderecoCliente(Integer clienteId) throws ObjectNotFoundException {
		List<EnderecoDTO>  enderecos = enderecoRepository.findByCliente_Id(clienteId);
		if( enderecos.isEmpty()) {
			throw new ObjectNotFoundException("Endereco não encontrado.");
		}
		return enderecos;
	}
}
