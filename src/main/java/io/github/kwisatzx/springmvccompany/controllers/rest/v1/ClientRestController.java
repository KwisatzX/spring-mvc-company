package io.github.kwisatzx.springmvccompany.controllers.rest.v1;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.DuplicateException;
import io.github.kwisatzx.springmvccompany.controllers.exceptions.NotFoundException;
import io.github.kwisatzx.springmvccompany.model.client.Client;
import io.github.kwisatzx.springmvccompany.model.client.dto.ClientGetDto;
import io.github.kwisatzx.springmvccompany.model.client.dto.ClientInputDto;
import io.github.kwisatzx.springmvccompany.model.client.dto.ClientModelMapper;
import io.github.kwisatzx.springmvccompany.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ClientRestController {

    private final ClientService service;
    private final ClientModelMapper mapper;

    @GetMapping("/clients")
    public ResponseEntity<Collection<ClientGetDto>> getAllClients() { //TODO pagination
        return ResponseEntity.ok(service.getAllClients().stream().map(mapper::clientToClientGetDto).toList());
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientGetDto> getClientById(@PathVariable Long id) {
        Optional<Client> result = service.findById(id);
        if (result.isPresent()) return ResponseEntity.ok(mapper.clientToClientGetDto(result.get()));
        else throw new NotFoundException();
    }

    @PostMapping("/clients/new")
    public ResponseEntity<Void> createNewClient(@RequestBody ClientInputDto body) {
        //errors
        if (service.existsByName(body.name())) throw new DuplicateException();
        else {
            Client saved = service.save(mapper.clientInputDtoToClient(body));
            return ResponseEntity.created(URI.create("/clients/" + saved.getId())).build();
        }
    }
}
