package br.com.edson.cursoSpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.edson.cursoSpring.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}
