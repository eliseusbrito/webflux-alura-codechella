package br.com.eliseu.codechella;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repositorio;

    public Flux<EventoDto> obterTodos() {
        return repositorio.findAll().map(EventoDto::toDto);
    }

    public Mono<EventoDto> obterById(Long id) {
        return repositorio.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(EventoDto::toDto);
    }

    public Mono<EventoDto> cadastrar(EventoDto eventoDto) {
        return repositorio.save(eventoDto.toEntity()).map(EventoDto::toDto);
    }

    public Mono<Void> excluir(Long id) {
        return repositorio.deleteById(id);
    }

    public Flux<EventoDto> obterPorTipo(String tipo) {
        TipoEvento tipoEvento = TipoEvento.valueOf(tipo.toUpperCase());
        return repositorio.findByTipo(tipoEvento).map(EventoDto::toDto);
    }
}
