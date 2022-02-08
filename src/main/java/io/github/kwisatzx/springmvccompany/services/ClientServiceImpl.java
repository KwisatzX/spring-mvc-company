package io.github.kwisatzx.springmvccompany.services;

import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.client.Client;
import io.github.kwisatzx.springmvccompany.repositories.BranchRepository;
import io.github.kwisatzx.springmvccompany.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Component
public class ClientServiceImpl implements ClientService {

    public final ClientRepository clientRepository;
    public final BranchRepository branchRepository;

    @Override public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override public Collection<Client> getAllClients() {
        return (Collection<Client>) clientRepository.findAll();
    }

    @Override public Collection<Client> findAllByIds(Set<Long> ids) {
        return (Collection<Client>) clientRepository.findAllById(ids);
    }

    @Override public Optional<Branch> findBranchById(Long id) {
        return branchRepository.findById(id);
    }

    @Override public boolean existsByName(String name) {
        return clientRepository.existsByName(name);
    }
}
