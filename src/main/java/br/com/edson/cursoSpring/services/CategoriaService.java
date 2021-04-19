package br.com.edson.cursoSpring.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.edson.cursoSpring.exceptions.IntegridadeViolada;
import br.com.edson.cursoSpring.exceptions.ObjectNotFound;
import br.com.edson.cursoSpring.model.Categoria;
import br.com.edson.cursoSpring.model.dto.CategoriaDTO;
import br.com.edson.cursoSpring.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Page<CategoriaDTO> all(Pageable pageRequest) throws ObjectNotFound {
		Page<Categoria> list = this.categoriaRepository.findAll(pageRequest);
		if ( list.isEmpty()) {
			throw new ObjectNotFound("Não existe categorias registradas.");
		}
		Page<CategoriaDTO> listDTO = list.map(dto -> new CategoriaDTO(dto));
		return listDTO;
	}
	
	public List<CategoriaDTO> all() throws ObjectNotFound {
		List<Categoria> list = this.categoriaRepository.findAll();
		if ( list.isEmpty()) {
			throw new ObjectNotFound("Não existe categorias registradas.");
		}
		List<CategoriaDTO> listDTO = list.stream().map(cat -> new CategoriaDTO(cat))
				.collect(Collectors.toList());
		return listDTO;
	}

	public Categoria buscar(Integer id) throws ObjectNotFound {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFound("Categoria não encontrada."));
	}


	public void delete(Integer id) throws ObjectNotFound, IntegridadeViolada {
		if (categoriaRepository.existsById(id)) {
			try {
				categoriaRepository.deleteById(id);
			} catch (DataIntegrityViolationException e) {
				throw new IntegridadeViolada("Esta exclusão viola as regras do negócio. "
						+ " Categoria tem produtos vinculados a ela.");
			}
		} else {
			throw new ObjectNotFound("Categoria não encontrada.");
		}
	}
	
	@Transactional
	public Categoria save(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	@Transactional
	public List<Categoria> salvarAll(final List<Categoria> categorias) throws Exception {
		try {
			return categoriaRepository.saveAll(categorias);
		} catch (Exception e) {
			throw new Exception("falha ao salvar");
		}
	}

	@Transactional
	public Categoria update(Categoria categoria, Integer id) throws ObjectNotFound {
		if (categoriaRepository.existsById(id)) {
			categoria.setId(id);
			return categoriaRepository.save(categoria);
		}
		throw new ObjectNotFound("Categoria não encontrada.");
	}

}
