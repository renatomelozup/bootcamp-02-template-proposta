package br.com.zup.renatomelo.proposta.proposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/dados")
public class PropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    @PostMapping
    public ResponseEntity<?> receberDados(@RequestBody @Valid NovaPropostaRequest novaProposta,
                                          UriComponentsBuilder uriComponentsBuilder) {

        if(propostaRepository.findByDocumento(novaProposta.getDocumento()).isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Proposta proposta = novaProposta.toModel();

        propostaRepository.save(proposta);

        URI uri = uriComponentsBuilder.path("/api/dados/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
