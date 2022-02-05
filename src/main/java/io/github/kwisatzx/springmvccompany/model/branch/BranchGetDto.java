package io.github.kwisatzx.springmvccompany.model.branch;

import java.time.LocalDate;
import java.util.Set;

public record BranchGetDto(
        Long id,
        String name,
        Long managerId,
        LocalDate mgrStartDate,
        Set<Long> clientIds,
        Set<Long> employeeIds
) {}
