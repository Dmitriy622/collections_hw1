package org.example.service;

import org.example.exception.EmployeeNotFoundException;
import org.example.exception.EmployeeStorageIsFullException;
import org.example.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.*;

public class EmployeeServiceTest {
    private final EmployeeService employeeService = new EmployeeService(new ValidationService());

    @AfterEach
    public void afterEach() {
        Collection<Employee> copy = new ArrayList<>(employeeService.findAll());
        for (Employee employee : copy) {
            employeeService.remove(employee.getFirstName(), employee.getLastName());
        }
    }

    @BeforeEach
    public void beforeEach() {
        employeeService.add("Ivan", "Ivanov", 1, 10000);
        employeeService.add("Ivan", "Petrov", 1, 20000);
        employeeService.add("Petr", "Ivanov", 2, 30000);
        employeeService.add("Oleg", "Ivanov", 2, 40000);
    }

    @Test
    void addPositiveTest() {
        Employee expected = new Employee("Gerasim", "Gerasimov", 3, 45000);
        employeeService.add("Gerasim", "Gerasimov", 3, 45000);
        assertThatNoException().isThrownBy(() -> employeeService.find("Gerasim", "Gerasimov"));
        assertThat(employeeService.find("Gerasim", "Gerasimov")).isEqualTo(expected);
        assertThat(employeeService.findAll()).contains(expected);
    }

    @Test
    void addNegative1Test() {
        for (int i = 0; i < 6; i++) {
            char f = (char) ('g' + i);
            employeeService.add("Gerasim" + f, "Gerasimov" + f, 3, 45000);
        }
        assertThat(employeeService.findAll()).hasSize(10);

        assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() -> employeeService.add("Gerasim", "Gerasimov", 3, 45000));
    }

    @Test
    void addNegative2Test() {
        Employee employee = new Employee("Ivan", "Ivanov", 1, 10000);
        assertThat(employeeService.findAll()).contains(employee);
        assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() -> employeeService.add("Ivan", "Ivanov", 1, 10000));
    }

    @Test
    void removePositiveTest() {
        Employee expected = new Employee("Ivan", "Ivanov", 1, 10000);
        assertThat(employeeService.findAll()).contains(expected);
        employeeService.remove("Ivan", "Ivanov");
        assertThat(employeeService.findAll()).doesNotContain(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.remove("Ivan", "Ivanov"));
    }

    @Test
    void removeNegativeTest() {
        Employee expected = new Employee("Nikolay", "Nikolayev", 1, 15000);
        assertThat(employeeService.findAll()).contains(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.remove("Nikolay", "Nikolayev"));
    }

    @Test
    void findPositiveTest() {
        Employee expected = new Employee("Ivan", "Ivanov", 1, 10000);
        assertThat(employeeService.findAll()).contains(expected);
        assertThat(employeeService.find("Ivan", "Ivanov"));
    }

    @Test
    void findNegativeTest() {
        Employee expected = new Employee("Nikolay", "Nikolayev", 1, 15000);
        assertThat(employeeService.findAll()).contains(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.find("Nikolay", "Nikolayev"));
    }

    @Test
    void findAll() {
        assertThat(employeeService.findAll())
                .containsExactlyInAnyOrder(
                        new Employee("Ivan", "Ivanov", 1, 10000),
                        new Employee("Ivan", "Petrov", 1, 20000),
                        new Employee("Petr", "Ivanov", 2, 30000),
                        new Employee("Oleg", "Ivanov", 2, 40000)
                );
    }
}
