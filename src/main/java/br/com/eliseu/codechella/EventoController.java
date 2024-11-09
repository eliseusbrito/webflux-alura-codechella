package br.com.eliseu.codechella;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService servico;

    @GetMapping()
    public Flux<EventoDto> obterTodos() {
        return servico.obterTodos();
    }

    @GetMapping("/{id}")
    public Mono<EventoDto> obterById(@PathVariable Long id) {
        return servico.obterById(id);
    }

    @PostMapping()
    public Mono<EventoDto> cadastrar(@RequestBody EventoDto eventoDto) {
        return servico.cadastrar(eventoDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> excluir(@PathVariable Long id) {
        return servico.excluir(id);
    }

    @GetMapping(value = "/categoria/{tipo}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoDto> obterPorTipo(@PathVariable String tipo) {
        return Flux.from(servico.obterPorTipo(tipo)).delayElements(Duration.ofSeconds(4));
    }

}