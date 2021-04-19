package br.com.edson.cursoSpring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.edson.cursoSpring.exceptions.ObjectNotFound;
import br.com.edson.cursoSpring.model.dto.CidadeDTO;
import br.com.edson.cursoSpring.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	
	public List<CidadeDTO> findAll(Integer estadoId) throws ObjectNotFound{
		List<CidadeDTO> cidades = cidadeRepository.findAllByEstado_IdOrderByNome(estadoId);
		if ( cidades.isEmpty()) {
			throw new ObjectNotFound("Não há cidades a exibir");
		}
		return cidades;
	}
}
