package org.example.service;

import org.assertj.core.api.Assertions;
import org.example.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    private final Collection<Employee> employees = List.of(
            new Employee("Ivan", "Ivanov", 1, 10000),
            new Employee("Ivan", "Petrov", 1, 20000),
            new Employee("Petr", "Ivanov", 2, 30000),
            new Employee("Oleg", "Ivanov", 2, 40000),
            new Employee("Valentin", "Kotov", 3, 50000)
    );

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void beforeEach() {
        when(employeeService.findAll()).thenReturn(employees);
    }

    @Test
    public void getMaxSalaryPositiveTest() {
        int expected = 20000;
        assertThat(departmentService.getMaxSalary(1)).isEqualTo(expected);
    }

    @Test
    public void getMaxSalaryNegativeTest() {
        assertThat(departmentService.getMaxSalary(4)).isNull();
    }

    @Test
    public void getMinSalaryPositiveTest() {
        int expected = 30000;
        assertThat(departmentService.getMinSalary(2)).isEqualTo(expected);
    }

    @Test
    public void getMinSalaryNegativeTest() {
        assertThat(departmentService.getMinSalary(4)).isNull();
    }

    @Test
    public void getSumSalaryPositiveTest() {
        int expected = 30000;
        assertThat(departmentService.getSumOfSalaries(1)).isEqualTo(expected);
    }

    @Test
    public void getSumSalaryNegativeTest() {
        assertThat(departmentService.getSumOfSalaries(4)).isNull();
    }

    @Test
    public void getEmployeesFromDepartmentTest() {
        assertThat(departmentService.getEmployeesFromDepartment(2))
                .containsExactlyInAnyOrder(
                        new Employee("Petr", "Ivanov", 2, 30000),
                        new Employee("Oleg", "Ivanov", 2, 40000)
                );
    }

    @Test
    public void getEmployeesGroupedByDepartmentPositiveTest() {
        assertThat(departmentService.getEmployeesGroupedByDepartment()).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        1, List.of(
                                new Employee("Ivan", "Ivanov", 1, 10000),
                                new Employee("Ivan", "Petrov", 1, 20000)
                        ),
                        2, List.of(
                                new Employee("Petr", "Ivanov", 2, 30000),
                                new Employee("Oleg", "Ivanov", 2, 40000)
                        ),
                        3, List.of(
                                new Employee("Valentin", "Kotov", 3, 50000)
                        )
                )
        );
    }


}
