package io.github.kwisatzx.springmvccompany.model.branch.dto;

import java.time.LocalDate;
import java.util.Set;

public record BranchInputDto(
        String name,
        Long managerId,
        LocalDate mgrStartDate,
        Set<Long> clientIds,
        Set<Long> employeeIds
) {}
