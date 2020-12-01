package br.com.zup.renatomelo.proposta.proposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/dados")
public class PropostaController {

    @Autowired
    private VerificaCpfCnpjValidator verificaCpfCnpjValidator;

    @PersistenceContext
    private EntityManager entityManager;

    @InitBinder
    private void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(verificaCpfCnpjValidator);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> receberDados(@RequestBody @Valid NovaPropostaRequest novaProposta,
                                          UriComponentsBuilder uriComponentsBuilder) {

        Proposta proposta = novaProposta.toModel();

        entityManager.persist(proposta);

        URI uri = uriComponentsBuilder.path("/api/dados/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
