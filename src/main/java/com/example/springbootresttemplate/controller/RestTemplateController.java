package com.example.springbootresttemplate.controller;

import com.example.springbootresttemplate.model.Employee;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestTemplateController {
    private final String URI_EMPLOYEE = "http://localhost:8080/employees";
    private final String URI_EMPLOYEE_ID = "http://localhost:8080/employees/{id}";

    @Autowired
    RestTemplate restTemplate;

    /* ===========Get API=========*/
    /*
    getForObject(): return the resource object directly. We use it when we want to map response directly to resource DTO.
    * */

    //Get all
    @GetMapping("/v1/allEmployees")
    public ResponseEntity getAllV1() {
        Employee[] employees = restTemplate.getForObject(URI_EMPLOYEE, Employee[].class);

        return new ResponseEntity < > (Arrays.asList(employees), HttpStatus.OK);
    }

    //Get by Id
    @GetMapping("/v1/employees/{id}")
    public ResponseEntity getByIdV1(@PathVariable("id") final Integer id) {

        try {
            Map<String, String> params = new HashMap<>();
            params.put("id", String.valueOf(id));
            Employee employee = restTemplate.getForObject(URI_EMPLOYEE_ID, Employee.class, params);

            return new ResponseEntity < >(employee, HttpStatus.OK);
        } catch (NoResultException e) {
            throw new NoResultException(e.getMessage());
        }
    }

    /*
    getForEntity(): return ResponseEntity which contains both the status code and resource as an object.
    We can use it when we want to get a response like JSON.
    * */

    //Get all
    @GetMapping("/v2/allEmployees")
    public ResponseEntity getAllV2() {
        ResponseEntity<Employee[]> responseEntity = restTemplate.getForEntity(URI_EMPLOYEE, Employee[].class);

        return responseEntity;
    }

    //Get by Id
    @GetMapping("/v2/employees/{id}")
    public ResponseEntity getByIdV2(@PathVariable("id") final Integer id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        ResponseEntity<Employee> responseEntity = restTemplate.getForEntity(URI_EMPLOYEE_ID, Employee.class, params);

        return responseEntity;
    }

    /*
    exchange():
    * */

    //Get all
    @GetMapping("/v3/allEmployees")
    public ResponseEntity getAllV3() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(URI_EMPLOYEE, HttpMethod.GET, httpEntity, Employee[].class);
    }

    //Get By Id
    @GetMapping("/v3/employees/{id}")
    public ResponseEntity getByIdV3(@PathVariable("id") Integer id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(URI_EMPLOYEE + id, HttpMethod.GET, entity, Employee.class);
    }

    /* =========Post API=========== */

    /*
    postForLocation(): will fire a POST request which will take URI, employee request body and return the location of the newly created resource.
    */
    @PostMapping("/v1/employees")
    public ResponseEntity addEmployeeV1(@RequestBody Employee employee) {
        URI createUri = restTemplate.postForLocation(URI_EMPLOYEE, employee, Employee.class);

        return ResponseEntity.created(createUri).build();
    }

    /*
    postForObject(): will fire a POST request which will take URI, employee request body, and responseType as input and return the resource object.
     */
    @PostMapping("/v2/employees")
    public ResponseEntity addEmployeeV2(@RequestBody final Employee newEmployee) {
        Employee createEmployee = restTemplate.postForObject(URI_EMPLOYEE, newEmployee, Employee.class);

        return new ResponseEntity(createEmployee, HttpStatus.OK);
    }

    /*
    postForEntity(): will fire a POST request which will take URI, employee request body, and responseType as input and return the resource as JSON.
     */
    @PostMapping("/v3/employees")
    public ResponseEntity addEmployeeV3(@RequestBody Employee employee) {
        ResponseEntity<Employee> response = restTemplate.postForEntity(URI_EMPLOYEE, employee, Employee.class);

        return response;
    }

    /*
    exchange():
     */
    @PostMapping("/v4/employees")
    public ResponseEntity addEmployeeV4(@RequestBody final Employee employee) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> entity = new HttpEntity<>(employee, httpHeaders);

        return restTemplate.exchange(URI_EMPLOYEE, HttpMethod.POST, entity, Employee.class);
    }

    /* =====Update Employee==== */

    @PutMapping("/v1/employees/{id}")
    public ResponseEntity updateEmployeeV1(@PathVariable Integer id, @RequestBody Employee employee) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        restTemplate.put(URI_EMPLOYEE_ID, employee, params);

        return new ResponseEntity("Employee Updated with ID: " + id, HttpStatus.OK);
    }

    @PutMapping("/v2/employees/{id}")
    public ResponseEntity updateEmployeeV2(@PathVariable Integer id, @RequestBody Employee employee) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> entity = new HttpEntity<>(employee, httpHeaders);

        return restTemplate.exchange(URI_EMPLOYEE + id, HttpMethod.PUT, entity, Employee.class);
    }

    /* =====Delete Employee===== */

    @DeleteMapping("/v1/employees/{id}")
    public ResponseEntity deleteEmployeeV1(@PathVariable Integer id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        restTemplate.delete(URI_EMPLOYEE, params);

        return new ResponseEntity("Employee deleted wit ID " + id, HttpStatus.OK);
    }

    @DeleteMapping("/v2/employees/{id}")
    public ResponseEntity deleteEmployeeV2(@PathVariable Integer id, @RequestBody final Employee employee) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> entity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(URI_EMPLOYEE + id, HttpMethod.DELETE, entity, Employee.class);
    }
}
