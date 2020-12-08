package br.com.zup.renatomelo.proposta.proposta.service;

import br.com.zup.renatomelo.proposta.proposta.response.CartaoResponse;
import br.com.zup.renatomelo.proposta.proposta.request.EnvioDadosLegado;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${cartao.host}", name = "cartao")
public interface CartaoClient {

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    void enviarDados(@RequestBody EnvioDadosLegado envioDadosLegado);

    @RequestMapping(method = RequestMethod.GET, consumes = "application/json")
    CartaoResponse checarCartao(@RequestParam String idProposta);
}
