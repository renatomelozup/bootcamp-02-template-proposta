package br.com.zup.renatomelo.proposta.proposta.repository;

import br.com.zup.renatomelo.proposta.proposta.model.Proposta;
import br.com.zup.renatomelo.proposta.proposta.model.StatusProposta;
import br.com.zup.renatomelo.proposta.proposta.response.DadosPropostaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, UUID> {

    Optional<Proposta> findByDocumento(String documento);
    List<Proposta> findAllByStatusAndCartaoId(StatusProposta statusProposta, String cartaoId);
    Optional<DadosPropostaProjection> getById(UUID id);
}
