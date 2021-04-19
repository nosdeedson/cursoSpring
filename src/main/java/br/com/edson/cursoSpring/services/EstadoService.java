package br.com.edson.cursoSpring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.edson.cursoSpring.exceptions.ObjectNotFound;
import br.com.edson.cursoSpring.model.dto.EstadoDTO;
import br.com.edson.cursoSpring.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<EstadoDTO> findAll() throws ObjectNotFound{
		List<EstadoDTO> estados = estadoRepository.findAllByOrderByNome();
		
		if ( estados.isEmpty()) {
			throw new ObjectNotFound("Não há estados há listar.");
		}
		
		return estados;
	}

}
