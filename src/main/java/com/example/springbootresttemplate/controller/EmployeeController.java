package com.example.springbootresttemplate.controller;

import com.example.springbootresttemplate.model.Employee;
import com.example.springbootresttemplate.service.EmployeeService;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    //Get all employees
    @GetMapping("/employees")
    private List getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    //Get Employee by id
    @GetMapping("/employees/{id}")
    private Employee getEmployeeById(@PathVariable("id") int id) {
        try {
            return employeeService.getEmployeeById(id);
        } catch (NoResultException e) {
            throw new NoResultException(e.getMessage());
        }
    }

    //add employee
    @PostMapping("/employees")
    private Employee createEmployee(@RequestBody Employee employee) {
        employeeService.saveOrUpdate(employee);
        return employee;
    }

    //update employee
    @PutMapping("/employee/{id}")
    private Employee updateEmployeeById(@PathVariable("id") int id, @RequestBody Employee employee) {
        Employee updateEmployee = employeeService.getEmployeeById(id);
        updateEmployee.setName(employee.getName());
        updateEmployee.setSalary(employee.getSalary());
        employeeService.saveOrUpdate(updateEmployee);
        return updateEmployee;
    }

    //delete employee
    @DeleteMapping("employees/{id}")
    private Employee deleteEmployeeById(@PathVariable("id") int id) {
        Employee deleteEmployee = employeeService.getEmployeeById(id);
        employeeService.deleteById(id);
        return deleteEmployee;
    }
}
