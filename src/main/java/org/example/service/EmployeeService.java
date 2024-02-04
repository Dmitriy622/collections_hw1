package org.example.service;

import org.example.exception.EmployeeAlreadyAddedException;
import org.example.exception.EmployeeNotFoundException;
import org.example.exception.EmployeeStorageIsFullException;
import org.example.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {
    private static final int MAX_EMPLOYEES = 10;
    private final List<Employee> employees = new ArrayList<>();

    public void add(String firstName, String lastName) {
        if (employees.size() == MAX_EMPLOYEES) {
            throw new EmployeeStorageIsFullException();
        }
        Employee employee = new Employee(firstName, lastName);
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException();
        }
        employees.add(employee);
    }

    public Employee remove(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (!employees.remove(employee)) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public Employee find(String firstName, String lastName) {
        Employee employee = new Employee(firstName, lastName);
        if (!employees.contains(employee)) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public List<Employee> findAll() {
        return Collections.unmodifiableList(employees);
    }
}
