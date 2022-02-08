package io.github.kwisatzx.springmvccompany.model.client.dto;

public record ClientGetDto(
        Long id,
        String name,
        Long branchId
) {}
