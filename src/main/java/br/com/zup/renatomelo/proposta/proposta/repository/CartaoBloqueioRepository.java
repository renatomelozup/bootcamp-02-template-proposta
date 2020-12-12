package br.com.zup.renatomelo.proposta.proposta.repository;

import br.com.zup.renatomelo.proposta.proposta.model.CartaoBloqueio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartaoBloqueioRepository extends JpaRepository<CartaoBloqueio, UUID> {
}
