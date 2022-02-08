package io.github.kwisatzx.springmvccompany.repositories;

import io.github.kwisatzx.springmvccompany.model.client.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

    boolean existsByName(String name);
}
