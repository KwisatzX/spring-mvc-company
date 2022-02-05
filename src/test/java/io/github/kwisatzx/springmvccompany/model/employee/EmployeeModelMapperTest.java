package io.github.kwisatzx.springmvccompany.model.employee;

import io.github.kwisatzx.springmvccompany.model.branch.Branch;
import io.github.kwisatzx.springmvccompany.model.employee.dto.EmployeeGetDto;
import io.github.kwisatzx.springmvccompany.model.employee.dto.EmployeeInputDto;
import io.github.kwisatzx.springmvccompany.model.employee.dto.EmployeeModelMapper;
import io.github.kwisatzx.springmvccompany.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EmployeeModelMapperTest {

    @Autowired EmployeeModelMapper mapper;
    @Mock EmployeeService employeeServiceMock;
    @Mock Branch branchMock;
    @Mock Employee superiorMock;

    @Test
    void employeeToEmployeeGetDto() {
        when(superiorMock.getId()).thenReturn(999L);
        when(branchMock.getId()).thenReturn(888L);

        Employee employee = new Employee();
        employee.setId(0L);
        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setSex('M');
        employee.setBirthDay(LocalDate.of(1990, 10, 10));
        employee.setSalary(999.9);
        employee.setSuperior(superiorMock);
        employee.setBranch(branchMock);

        EmployeeGetDto employeeGetDto = mapper.employeeToEmployeeGetDto(employee);
        assertEquals(employee.getId(), employeeGetDto.id());
        assertEquals(employee.getFirstName(), employeeGetDto.firstName());
        assertEquals(employee.getLastName(), employeeGetDto.lastName());
        assertEquals(employee.getSex(), employeeGetDto.sex());
        assertEquals(employee.getBirthDay(), employeeGetDto.birthDay());
        assertEquals(employee.getSalary(), employeeGetDto.salary());
        assertEquals(employee.getSuperior().getId(), employeeGetDto.superiorId());
        assertEquals(employee.getBranch().getId(), employeeGetDto.branchId());
    }

    @Test
    void employeeInputDtoToEmployee() {
        when(superiorMock.getId()).thenReturn(999L);
        when(branchMock.getId()).thenReturn(888L);
        when(employeeServiceMock.findById(999L)).thenReturn(Optional.of(superiorMock));
        when(employeeServiceMock.findBranchById(888L)).thenReturn(Optional.of(branchMock));
        mapper.setEmployeeService(employeeServiceMock);

        EmployeeInputDto employeeInputDto = new EmployeeInputDto("John", "Smith", LocalDate.of(1990, 10, 10),
                                                                 'M', 999.9, 999L, 888L);

        Employee employee = mapper.employeeInputDtoToEmployee(employeeInputDto);
        assertEquals(employeeInputDto.firstName(), employee.getFirstName());
        assertEquals(employeeInputDto.lastName(), employee.getLastName());
        assertEquals(employeeInputDto.sex(), employee.getSex());
        assertEquals(employeeInputDto.birthDay(), employee.getBirthDay());
        assertEquals(employeeInputDto.salary(), employee.getSalary());
        assertEquals(employeeInputDto.superiorId(), employee.getSuperior().getId());
        assertEquals(employeeInputDto.branchId(), employee.getBranch().getId());
    }
}