package org.example.service;

import org.example.exception.EmployeeAlreadyAddedException;
import org.example.exception.EmployeeNotFoundException;
import org.example.exception.EmployeeStorageIsFullException;
import org.example.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {
    private static final int MAX_EMPLOYEES = 10;
    private final Map<String, Employee> employees = new HashMap<>();

    public void add(String firstName, String lastName) {
        if (employees.size() == MAX_EMPLOYEES) {
            throw new EmployeeStorageIsFullException();
        }
        String key = buildKey(firstName, lastName);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException();
        }
        employees.put(key, new Employee(firstName, lastName));
    }

    public Employee remove(String firstName, String lastName) {
        String key = buildKey(firstName, lastName);
        Employee employee = employees.remove(key);
        if (employee == null) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public Employee find(String firstName, String lastName) {
        String key = buildKey(firstName, lastName);
        Employee employee = employees.get(key);
        if (employee == null) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public Collection<Employee> findAll() {
        return Collections.unmodifiableCollection(employees.values());
    }

    private String buildKey(String name, String surname) {
        return name + surname;
    }
}
