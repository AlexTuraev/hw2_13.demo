package hw2_13.com.example.hw2_13.demo.controllers;

import hw2_13.com.example.hw2_13.demo.services.EmployeeService;
import hw2_13.com.example.hw2_13.demo.services.departmentservice.DepartmentService;
import hw2_13.com.example.hw2_13.demo.staff_info.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/max-salary")
    public Employee getEmployeeMaxSalaryByDepartment(@RequestParam("departmentId") int departmentId) {
        return departmentService.getEmployeeMaxSalaryByDep(departmentId)
                .orElseThrow(()->new RuntimeException("Employee with max salary not found in " + departmentId));
    }

    @GetMapping("/min-salary")
    public Employee getEmployeeMinSalaryByDepartment(@RequestParam("departmentId") int departmentId){
        return departmentService.getEmployeeMinSalaryByDep(departmentId)
                .orElseThrow();
    }

    @GetMapping("/all")
    public List<Employee> getEmployeesByDepartment(@RequestParam(defaultValue = "-1") int departmentId) {
        return departmentService.getAllEmployeesByDepartment(departmentId);
    }
}
