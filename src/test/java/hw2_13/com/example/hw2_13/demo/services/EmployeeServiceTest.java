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
    void addNewEmployee(String surname, String name, String secondName, int department, float salary) {
        // Проверка того, что возвращается ожидаемая expected строка
        Employee actual = employeeService.addNewEmployee(surname, name, secondName, department, salary);
        Employee expected = new Employee(surname, name, secondName, department, salary);
        assertEquals(expected, actual);

        // Проверка того, что элемент действительно вошел в коллекцию после добавления
        provideStartContent(); // добавляются еще 3 элемента Employee

        // создаем ожидаемый List для сравнения с этими же Employee
        List <Employee> employeeList = List.of(
                new Employee(surname, name, secondName, department, salary),
                new Employee("Sidorkin", "petr", "ivaNovich", 2, 45000),
                new Employee("МосКВин", "иван", "Геннадьевич", 5, 180000),
                new Employee("Sidorov", "ivan", "Ivanovich", 5, 150000)
        );

        // Проверяем, что листы из emploeeService и ожидаемый "эталонный" совпадают (порядок не имеет значения)
        Assertions.assertThat(employeeService.getEmployeeList()).containsExactlyInAnyOrderElementsOf(employeeList);
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
                Arguments.of("Fedorov", "petr", "ivaNovich", 2, 85000),
                Arguments.of("Головачев", "иван", "Геннадьевич", 5, 180000),
                Arguments.of("Kolesnikov", "ivan", "Ivanovich", 5, 550000)
        );
    }

    @Test
    void deleteEmployeeTestReturnString() {
        // Проверка возвращаемой строки при успешном удалении
        employeeService.addNewEmployee("Sidorkin", "petr", "ivaNovich", 2, 45000);
        Employee actual = employeeService.deleteEmployee("Sidorkin", "petr", "ivaNovich");
        Employee expected = new Employee("Sidorkin", "petr", "ivaNovich", 2, 45000);
        assertEquals(actual, expected);

        // Проверка возвращаемой строки, если не найден такой сотрудник
        actual = employeeService.deleteEmployee("Sidorkin", "petr", "ivaNovich");
        assertNull(actual);
    }

    @Test
    void deleteEmployeeTestEmployeesListAFterDeleting() {
        // Проверка содержания списка сотрудников в Map.values после удаления
        provideStartContent(); // добавляем тестовый набор сотруников
        employeeService.deleteEmployee("МосКВин", "иван", "Геннадьевич"); // удаляем одного

        List <Employee> actualList = employeeService.getEmployeeList();

        // создаем ожидаемый List для сравнения с этими же Employee (без удаленного)
        List <Employee> expectedList = List.of(
                new Employee("Sidorkin", "petr", "ivaNovich", 2, 45000),
                new Employee("Sidorov", "ivan", "Ivanovich", 5, 150000)
        );

        // Проверяем, что листы из emploeeService и ожидаемый "эталонный" совпадают (порядок не имеет значения)
        Assertions.assertThat(actualList).containsExactlyInAnyOrderElementsOf(expectedList);
    }

    @Test
    void getListByDep() {
        provideStartContent();
        List <Employee> actualList = employeeService.getListByDep(5);

        List <Employee> expectedList = List.of(
                new Employee("МосКВин", "иван", "Геннадьевич", 5, 180000),
                new Employee("Sidorov", "ivan", "Ivanovich", 5, 150000)
        );
        Assertions.assertThat(actualList).containsExactlyInAnyOrderElementsOf(expectedList);
    }

    @Test
    void findEmployee() {
        provideStartContent();
        Employee actual = employeeService.findEmployee("Sidorkin", "petr", "ivaNovich");

        Employee expected = new Employee("Sidorkin", "petr", "ivaNovich", 2, 45000);
        assertEquals(expected, actual);
    }

    void provideStartContent() {
        employeeService.addNewEmployee("Sidorkin", "petr", "ivaNovich", 2, 45000);
        employeeService.addNewEmployee("МосКВин", "иван", "Геннадьевич", 5, 180000);
        employeeService.addNewEmployee("Sidorov", "ivan", "Ivanovich", 5, 150000);
    }
}