package hw2_13.com.example.hw2_13.demo.services;

import hw2_13.com.example.hw2_13.demo.exceptions.EmployeeAlreadyAddedException;
import hw2_13.com.example.hw2_13.demo.exceptions.EmployeeWrongNameException;
import hw2_13.com.example.hw2_13.demo.staff_info.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeServiceTest {
    private EmployeeService employeeService = new EmployeeService();

    @AfterEach
    void removeAllEmployees() {
        employeeService = new EmployeeService();
    }

    @Test
    void getEmployeeList() {

    }

    @ParameterizedTest
    @MethodSource("provideParamsFIO")
    void keyGenByFIO(String surname, String name, String secondName, String expectedString) {
        String actual = employeeService.keyGenByFIO(surname, name, secondName);
        assertEquals(expectedString, actual);
    }

    @Test
    void keyGenByFIOGenerateException() {
        assertThrows(EmployeeWrongNameException.class,
                ()->employeeService.keyGenByFIO("иванов", "Иван", "Ивано8?вич") );

    }
    public static Stream<Arguments> provideParamsFIO() {
        return Stream.of(
                Arguments.of("Иванов", "иван", "ИваНович", "ИВАНОВИВАНИВАНОВИЧ"),
                Arguments.of("Петров", "иван", "ИваНович", "ПЕТРОВИВАНИВАНОВИЧ"),
                Arguments.of("Сидоров", "иван", "сидорович", "СИДОРОВИВАНСИДОРОВИЧ")
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForAddNewEmployeeTest")
    void addNewEmployee(String surname, String name, String secondName, int department, float salary, String expected) {
        String actual = employeeService.addNewEmployee(surname, name, secondName, department, salary);
        assertEquals(expected, actual);
    }

    @Test
    void addNewEmployeeThatExisted() {
        employeeService.addNewEmployee("Sidorkin", "petr", "ivaNovich", 2, 45000);

        assertThrows(EmployeeAlreadyAddedException.class,
                ()->employeeService.addNewEmployee("Sidorkin", "Petr", "ivaNovich", 1, 55000)
                );
    }

    public static Stream<Arguments> provideParamsForAddNewEmployeeTest() {
        return Stream.of(
                Arguments.of("Sidorkin", "petr", "ivaNovich", 2, 45000, "Сотрудник: Sidorkin Petr Ivanovich ЗП = 45000.0 в отделе: 2 создан"),
                Arguments.of("МосКВин", "иван", "Геннадьевич", 5, 180000, "Сотрудник: Москвин Иван Геннадьевич ЗП = 180000.0 в отделе: 5 создан"),
                Arguments.of("Sidorov", "ivan", "Ivanovich", 5, 150000, "Сотрудник: Sidorov Ivan Ivanovich ЗП = 150000.0 в отделе: 5 создан")
        );
    }

    @Test
    void deleteEmployee() {
        employeeService.addNewEmployee("Sidorkin", "petr", "ivaNovich", 2, 45000);
        String actual = employeeService.deleteEmployee("Sidorkin", "petr", "ivaNovich");
        String expected = "Успешно удалены записи Sidorkin Petr Ivanovich";
        assertEquals(actual, expected);

        actual = employeeService.deleteEmployee("Sidorkin", "petr", "ivaNovich");
        expected = "Не найден сотрудник Sidorkin petr ivaNovich";
        assertEquals(expected, actual);
    }

    @Test
    void getListByDep() {
        provideStartContent();

        List<String> actualList = employeeService.getListByDep(5).stream()
                .map(e->e.getSurname() + e.getName() + e.getSecondName() + e.getDepartment() + e.getSalary())
                .collect(Collectors.toList());
        List<String> expectedList = List.of(
                "МосквинИванГеннадьевич5180000.0",
                "SidorovIvanIvanovich5150000.0"
        );
        assertEquals(expectedList, actualList);
    }

    @Test
    void findEmployee() {
        provideStartContent();
        Employee employee = employeeService.findEmployee("Sidorkin", "petr", "ivaNovich");
        String actual = employee.getSurname()+employee.getName()+employee.getSecondName()+employee.getDepartment()+employee.getSalary();
        String expected = "SidorkinPetrIvanovich245000.0";
        assertEquals(expected, actual);
    }

    void provideStartContent() {
        employeeService.addNewEmployee("Sidorkin", "petr", "ivaNovich", 2, 45000);
        employeeService.addNewEmployee("МосКВин", "иван", "Геннадьевич", 5, 180000);
        employeeService.addNewEmployee("Sidorov", "ivan", "Ivanovich", 5, 150000);
    }
}