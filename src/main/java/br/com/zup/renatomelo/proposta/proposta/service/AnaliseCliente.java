package br.com.zup.renatomelo.proposta.proposta.service;

import br.com.zup.renatomelo.proposta.proposta.request.EnvioDadosLegado;
import br.com.zup.renatomelo.proposta.proposta.response.ResultadoAnaliseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${analise.host}", name = "analise")
public interface AnaliseCliente {

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    ResultadoAnaliseResponse analise(@RequestBody EnvioDadosLegado envioDadosLegado);
}
