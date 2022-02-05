package io.github.kwisatzx.springmvccompany.model.employee.dto;

import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import io.github.kwisatzx.springmvccompany.services.EmployeeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class EmployeeModelMapper {

    protected EmployeeService employeeService;

    @Mapping(target = "superiorId", expression = "java(employee.getSuperior().getId())")
    @Mapping(target = "branchId", expression = "java(employee.getBranch().getId())")
    public abstract EmployeeGetDto employeeToEmployeeGetDto(Employee employee);

    @Mapping(target = "superior", expression = "java(employeeService.findById(employeeInputDto.superiorId()).get())")
    @Mapping(target = "branch", expression = "java(employeeService.findBranchById(employeeInputDto.branchId()).get())")
    public abstract Employee employeeInputDtoToEmployee(EmployeeInputDto employeeInputDto);

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
