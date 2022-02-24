package io.github.kwisatzx.springmvccompany.controllers.rest.v1;

import io.github.kwisatzx.springmvccompany.controllers.exceptions.DuplicateException;
import io.github.kwisatzx.springmvccompany.controllers.exceptions.InvalidEntityException;
import io.github.kwisatzx.springmvccompany.controllers.exceptions.NotFoundException;
import io.github.kwisatzx.springmvccompany.model.client.Client;
import io.github.kwisatzx.springmvccompany.model.client.dto.ClientGetDto;
import io.github.kwisatzx.springmvccompany.model.client.dto.ClientInputDto;
import io.github.kwisatzx.springmvccompany.model.client.dto.ClientModelMapper;
import io.github.kwisatzx.springmvccompany.model.client.validation.ClientInputDtoValidator;
import io.github.kwisatzx.springmvccompany.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ClientRestController {

    private final ClientService service;
    private final ClientModelMapper mapper;
    private final ClientInputDtoValidator validator;

    @InitBinder
    public void dataBinderInit(WebDataBinder dataBinder) {
        dataBinder.setValidator(validator);
    }

    @GetMapping("/clients")
    public ResponseEntity<Collection<ClientGetDto>> getAllClients() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::clientToClientGetDto).toList());
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientGetDto> getClientById(@PathVariable Long id) {
        Optional<Client> result = service.findById(id);
        if (result.isPresent()) return ResponseEntity.ok(mapper.clientToClientGetDto(result.get()));
        else throw new NotFoundException();
    }

    @GetMapping("/branches/{id}/clients")
    public ResponseEntity<Collection<ClientGetDto>> getClientsForBranch(@PathVariable Long id) {
        if (!service.branchExistsById(id)) throw new NotFoundException();
        else {
            Collection<Client> branchClients = service.findAllForBranch(service.findBranchById(id).get());
            return ResponseEntity.ok(branchClients.stream().map(mapper::clientToClientGetDto).toList());
        }
    }

    @PostMapping("/clients")
    public ResponseEntity<ClientGetDto> createNewClient(@RequestBody @Valid ClientInputDto body, BindingResult result)
            throws InvalidEntityException {
        if (result.hasErrors()) throw new InvalidEntityException(result);
        if (service.existsByName(body.name()))
            throw new DuplicateException("Client with name '" + body.name() + "' already exists");
        else {
            Client saved = service.save(mapper.clientInputDtoToClient(body));
            return ResponseEntity.created(URI.create("/clients/" + saved.getId()))
                    .body(mapper.clientToClientGetDto(saved));
        }
    }

    @Transactional
    @PutMapping("/clients/{id}")
    public ResponseEntity<ClientGetDto> editClient(@PathVariable Long id,
                                                   @RequestBody @Valid ClientInputDto body,
                                                   BindingResult result) throws InvalidEntityException {
        if (result.hasErrors()) throw new InvalidEntityException(result);
        Optional<Client> byId = service.findById(id);
        Client requestClient = mapper.clientInputDtoToClient(body);
        if (byId.isPresent()) {
            Client dbClient = byId.get();
            dbClient.setName(requestClient.getName());
            dbClient.setBranch(requestClient.getBranch());
            return ResponseEntity.noContent().location(URI.create("/clients/" + id)).build();
        } else {
            requestClient.setId(id);
            Client saved = service.save(requestClient);
            return ResponseEntity.created(URI.create("/clients/" + id)).
                    body(mapper.clientToClientGetDto(saved));
        }
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
