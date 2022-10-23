package hw2_13.com.example.hw2_13.demo.controllers;

import hw2_13.com.example.hw2_13.demo.services.EmployeeService;
import hw2_13.com.example.hw2_13.demo.staff_info.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/add")
    public Employee add(@RequestParam("surname") String surname,
                        @RequestParam("name") String name,
                        @RequestParam("secondName") String secondName,
                        @RequestParam("department") int department,
                        @RequestParam("salary") float salary){
        return employeeService.addNewEmployee(surname, name, secondName, department, salary);
    }

    @GetMapping("/remove")
    public Employee deleteEmployee(@RequestParam("surname") String surname,
                      @RequestParam("name") String name,
                      @RequestParam("secondName") String secondName){
        return employeeService.deleteEmployee(surname, name, secondName);
    }

    @GetMapping("/print")
    public List<Employee> getEmployeeList() {
        return employeeService.getEmployeeList();
    }

    @GetMapping("/find")
    public Employee find(@RequestParam("surname") String surname,
                                 @RequestParam("name") String name,
                                 @RequestParam("secondName") String secondName){
        return employeeService.findEmployee(surname, name, secondName);
    }
}

