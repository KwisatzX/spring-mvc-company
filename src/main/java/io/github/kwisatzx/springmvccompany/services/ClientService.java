package io.github.kwisatzx.springmvccompany.services;

import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.client.Client;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface ClientService {

    Optional<Client> findById(Long id);

    Client save(Client client);

    Collection<Client> getAllClients();

    Collection<Client> findAllByIds(Set<Long> ids);

    Optional<Branch> findBranchById(Long id);

    boolean existsByName(String name);
}
