package io.github.kwisatzx.springmvccompany.model.employee.dto;

import java.time.LocalDate;

public record EmployeeInputDto(
        String firstName,
        String lastName,
        LocalDate birthDay,
        Character sex,
        Double salary,
        Long superiorId,
        Long branchId
) {}
