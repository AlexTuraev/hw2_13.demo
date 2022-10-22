package hw2_13.com.example.hw2_13.demo.services.departmentservice;

import hw2_13.com.example.hw2_13.demo.services.EmployeeService;
import hw2_13.com.example.hw2_13.demo.staff_info.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Optional<Employee> getEmployeeMaxSalaryByDep(int departmentId) {
        return employeeService.getListByDep(departmentId)
                .stream().filter(e -> e.getDepartment() == departmentId)
                .max(Comparator.comparingDouble(employee -> employee.getSalary()));
    }

    @Override
    public Optional<Employee> getEmployeeMinSalaryByDep(int departmentId) {
        return employeeService.getListByDep(departmentId)
                .stream().filter(e -> e.getDepartment() == departmentId)
                .min(Comparator.comparingDouble(employee -> employee.getSalary()));
    }

    @Override
    // Возвращает сех сотрудниковЮ отсортированных по отделам
    public List<Employee> getAllEmployees() {
        return employeeService.getEmployeeList()
                .stream().sorted(Comparator.comparingInt(e -> e.getDepartment()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> getAllEmployeesByDepartment(int departmentId) {
        if (departmentId == -1) {
            return getAllEmployees();
        } else {
            return employeeService.getListByDep(departmentId)
                    .stream().filter(e -> e.getDepartment() == departmentId)
                    .collect(Collectors.toList());
        }
    }
}
