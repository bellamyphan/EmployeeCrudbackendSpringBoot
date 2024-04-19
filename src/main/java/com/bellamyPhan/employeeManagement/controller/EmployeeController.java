package com.bellamyPhan.employeeManagement.controller;

import com.bellamyPhan.employeeManagement.dao.EmployeeRepository;
import com.bellamyPhan.employeeManagement.exception.ResourceNotFoundException;
import com.bellamyPhan.employeeManagement.model.Employee;
import com.bellamyPhan.employeeManagement.model.Sequence;
import com.bellamyPhan.employeeManagement.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    // Create employee rest api
    @PostMapping("/employees")
    @Transactional
    @CrossOrigin(origins = "http://localhost:4200") // Specify allowed origin(s)
    public Employee createEmployee(@RequestBody Employee employee) {
        // generate sequence id
        employee.setId(sequenceGeneratorService.generateSequence(Sequence.SEQUENCE_NAME));
        // save employee to database
        return employeeRepository.save(employee);
    }

    // Get all employees
    @GetMapping("/employees")
    @CrossOrigin(origins = "http://localhost:4200") // Specify allowed origin(s)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get employee rest api
    @GetMapping("/employees/{id}")
    @CrossOrigin(origins = "http://localhost:4200") // Specify allowed origin(s)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));
        return ResponseEntity.ok(employee);
    }

    // Update employee rest api
    @PutMapping("/employees/{id}")
    @Transactional
    @CrossOrigin(origins = "http://localhost:4200") // Specify allowed origin(s)
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employeeDetails) {
        // Find the employee from the database.
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));
        // Update the employee.
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        // Save updated employee to the database.
        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // Delete employee rest api
    @DeleteMapping("/employees/{id}")
    @Transactional
    @CrossOrigin(origins = "http://localhost:4200") // Specify allowed origin(s)
    public ResponseEntity<Map<String, Boolean>> deleteEmployeeById(@PathVariable Integer id) {
        // retrieve the employee from the database
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));
        // delete the employee
        employeeRepository.delete(employee);
        // wrap up a message and boolean flag and return back
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
