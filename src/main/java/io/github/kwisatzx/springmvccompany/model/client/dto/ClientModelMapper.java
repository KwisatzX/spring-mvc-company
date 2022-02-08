package io.github.kwisatzx.springmvccompany.model.client.dto;

import io.github.kwisatzx.springmvccompany.model.client.Client;
import io.github.kwisatzx.springmvccompany.services.ClientService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ClientModelMapper {

    protected ClientService clientService;

    @Mapping(target = "branchId", expression = "java(client.getBranch().getId())")
    public abstract ClientGetDto clientToClientGetDto(Client client);

    @Mapping(target = "branch", expression = "java(clientService.findBranchById(clientInputDto.branchId()).get())")
    public abstract Client clientInputDtoToClient(ClientInputDto clientInputDto);

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

}
