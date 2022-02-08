package io.github.kwisatzx.springmvccompany.model.branch.dto;

import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.client.Client;
import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import io.github.kwisatzx.springmvccompany.services.BranchService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class BranchModelMapper {

    protected BranchService branchService;

    @Mapping(target = "managerId", expression = "java(branch.getManager().getId())")
    @Mapping(target = "clientIds", expression = "java(getClientIds(branch))")
    @Mapping(target = "employeeIds", expression = "java(getEmployeeIds(branch))")
    public abstract BranchGetDto branchToBranchGetDto(Branch branch);

    @Mapping(target = "manager", expression = "java(branchService.findEmployeeById(branchInputDto.managerId()).get())")
    @Mapping(target = "clients", expression = "java(branchService.findClientsByIds(branchInputDto.clientIds()))")
    @Mapping(target = "employees", expression = "java(branchService.findEmployeesByIds(branchInputDto.employeeIds()))")
    public abstract Branch branchInputDtoToBranch(BranchInputDto branchInputDto);

    protected Set<Long> getClientIds(Branch branch) {
        return branch.getClients().stream().map(Client::getId).collect(Collectors.toSet());
    }

    protected Set<Long> getEmployeeIds(Branch branch) {
        return branch.getEmployees().stream().map(Employee::getId).collect(Collectors.toSet());
    }

    @Autowired
    public void setBranchService(BranchService branchService) {
        this.branchService = branchService;
    }
}
