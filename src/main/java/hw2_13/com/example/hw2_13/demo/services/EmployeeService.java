package hw2_13.com.example.hw2_13.demo.services;

import hw2_13.com.example.hw2_13.demo.exceptions.EmployeeAlreadyAddedException;
import hw2_13.com.example.hw2_13.demo.exceptions.EmployeeWrongNameException;
import hw2_13.com.example.hw2_13.demo.staff_info.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private Map<String, Employee> employees; // <FIO, Employee>

    public EmployeeService() {
        employees = new HashMap<>();
    }

    public List<Employee> getEmployeeList() {
        return employees.values().stream().collect(Collectors.toList());
    }

    public String keyGenByFIO(String surname, String name, String secondName) {
        if (StringUtils.isAlpha(surname + name + secondName)) {
            return surname.toUpperCase() + name.toUpperCase() + secondName.toUpperCase();
        } else {
            throw new EmployeeWrongNameException("Невалидное ФИО сотрудника");
        }
    }

    public String addNewEmployee(String surname, String name, String secondName, int department, float salary) {
        String keyFIO = keyGenByFIO(surname, name, secondName);
        if (employees.containsKey(keyFIO)) {
            throw new EmployeeAlreadyAddedException("Такой сотрудник уже есть");
        }

        Employee newEmployee = new Employee(
                StringUtils.capitalize(surname.toLowerCase()),
                StringUtils.capitalize(name.toLowerCase()),
                StringUtils.capitalize(secondName.toLowerCase()),
                department,
                salary);

        employees.put(keyFIO, newEmployee);
        return "Сотрудник: " + newEmployee.getSurname() + " " + newEmployee.getName() + " " + newEmployee.getSecondName() +
                " ЗП = " + newEmployee.getSalary() + " в отделе: " + newEmployee.getDepartment() + " создан";
    }

    // Удаление элемента массива (сотрудника) по ФИО
    public String deleteEmployee(String surname, String name, String secondName) {
        String keyFIO = keyGenByFIO(surname, name, secondName);
        Employee deletedEmployee = employees.remove(keyFIO);
        if (deletedEmployee != null) {
            return "Успешно удалены записи " + deletedEmployee.getSurname() + " " + deletedEmployee.getName() + " " + deletedEmployee.getSecondName();
        } else {
            return "Не найден сотрудник " + surname + " " + name + " " + secondName;
        }
    }

    public List<Employee> getListByDep(int dep) { // получение списка List сотрудников департамента dep
        List <Employee> resultList = new ArrayList<>();

        for (Employee employee : employees.values()) {
            if (employee.getDepartment() == dep) {
                resultList.add(employee);
            }
        }
        return resultList;
    }

    public Employee findEmployee(String surname, String name, String secondName) {
        String keyFIO = keyGenByFIO(surname, name, secondName);
        if (employees.containsKey(keyFIO)) {
            return employees.get(keyFIO);
        } else {
            return null;
        }
    }
}