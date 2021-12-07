package io.github.kwisatzx.springmvccompany.repositories;

import io.github.kwisatzx.springmvccompany.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
