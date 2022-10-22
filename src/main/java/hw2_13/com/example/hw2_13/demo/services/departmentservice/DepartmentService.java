package hw2_13.com.example.hw2_13.demo.services.departmentservice;

import hw2_13.com.example.hw2_13.demo.staff_info.Employee;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Optional<Employee> getEmployeeMaxSalaryByDep(int departmentId);
    Optional<Employee> getEmployeeMinSalaryByDep(int departmentId);

    List<Employee> getAllEmployees();

    List<Employee> getAllEmployeesByDepartment(int departmentId);
}