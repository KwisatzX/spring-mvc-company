package io.github.kwisatzx.springmvccompany.model.branch;

import io.github.kwisatzx.springmvccompany.model.branch.dto.BranchGetDto;
import io.github.kwisatzx.springmvccompany.model.branch.dto.BranchInputDto;
import io.github.kwisatzx.springmvccompany.model.branch.dto.BranchModelMapper;
import io.github.kwisatzx.springmvccompany.model.client.Client;
import io.github.kwisatzx.springmvccompany.model.employee.Employee;
import io.github.kwisatzx.springmvccompany.services.BranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BranchModelMapperTest {

    @Autowired BranchModelMapper mapper;
    @Mock BranchService branchServiceMock;
    @Mock Employee empMock1;
    @Mock Employee empMock2;
    @Mock Client clientMock1;
    @Mock Client clientMock2;

    @BeforeEach
    void setUp() {
        when(empMock1.getId()).thenReturn(199L);
        when(empMock2.getId()).thenReturn(198L);
        when(clientMock1.getId()).thenReturn(99L);
        when(clientMock2.getId()).thenReturn(98L);
    }

    @Test
    void branchToBranchGetDto() {
        Branch branch = new Branch();
        branch.setId(0L);
        branch.setName("Branch name");
        branch.setManager(empMock1);
        branch.setMgrStartDate(LocalDate.parse("2010-10-10"));
        branch.setEmployees(Set.of(empMock1, empMock2));
        branch.setClients(Set.of(clientMock1, clientMock2));

        BranchGetDto branchGetDto = mapper.branchToBranchGetDto(branch);
        assertEquals(branch.getId(), branchGetDto.id());
        assertEquals(branch.getName(), branchGetDto.name());
        assertEquals(branch.getManager().getId(), branchGetDto.managerId());
        assertEquals(branch.getMgrStartDate(), branchGetDto.mgrStartDate());
        assertEquals(branch.getEmployees().stream().map(Employee::getId).collect(Collectors.toSet()),
                     branchGetDto.employeeIds());
        assertEquals(branch.getClients().stream().map(Client::getId).collect(Collectors.toSet()),
                     branchGetDto.clientIds());
    }

    @Test
    void branchInputDtoToBranch() {
        Set<Long> clientIds = Set.of(99L, 98L);
        Set<Long> employeeIds = Set.of(199L, 198L);
        BranchInputDto branchInputDto = new BranchInputDto("Branch name", 199L, LocalDate.parse("2010-10-10"),
                                                           clientIds, employeeIds);

        when(branchServiceMock.findEmployeeById(199L)).thenReturn(Optional.of(empMock1));
        when(branchServiceMock.findClientsByIds(clientIds)).thenReturn(Set.of(clientMock1, clientMock2));
        when(branchServiceMock.findEmployeesByIds(employeeIds)).thenReturn(Set.of(empMock1, empMock2));
        mapper.setBranchService(branchServiceMock);

        Branch branch = mapper.branchInputDtoToBranch(branchInputDto);
        assertEquals(branchInputDto.name(), branch.getName());
        assertEquals(branchInputDto.managerId(), branch.getManager().getId());
        assertEquals(branchInputDto.clientIds(),
                     branch.getClients().stream().map(Client::getId).collect(Collectors.toSet()));
        assertEquals(branchInputDto.employeeIds(),
                     branch.getEmployees().stream().map(Employee::getId).collect(Collectors.toSet()));
    }

}