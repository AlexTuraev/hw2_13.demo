package hw2_13.com.example.hw2_13.demo.services.departmentservice;

import hw2_13.com.example.hw2_13.demo.services.EmployeeService;
import hw2_13.com.example.hw2_13.demo.staff_info.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeServiceMock;

    @InjectMocks
    private DepartmentServiceImpl departmentService;


    @BeforeEach
    public void generateStartDepartmentList() {
        List<Employee> departmentList = List.of(
                new Employee("Sidorkin", "petr", "ivaNovich", 2, 45000),
                new Employee("МосКВин", "иван", "Геннадьевич", 5, 180000),
                new Employee("Sidorov", "ivan", "Ivanovich", 5, 150000),
                new Employee("Головин", "Алексей", "Петрович", 5, 100000),
                new Employee("Коробов", "Сергей", "Иванович", 2, 125000)

        );
        when(employeeServiceMock.getEmployeeList()).thenReturn(departmentList);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForMaxSalary")
    void getEmployeeMaxSalaryByDep(int depId, Employee expected) {
        assertThat(departmentService.getEmployeeMaxSalaryByDep(depId).orElse(null)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForMinSalary")
    void getEmployeeMinSalaryByDep(int depId, Employee expected) {
        assertThat(departmentService.getEmployeeMinSalaryByDep(depId).orElse(null)).isEqualTo(expected);
    }

    @Test
    void getAllEmployees() {
        assertThat(departmentService.getAllEmployees()).isEqualTo(
                List.of(
                        new Employee("Sidorkin", "petr", "ivaNovich", 2, 45000),
                        new Employee("Коробов", "Сергей", "Иванович", 2, 125000),
                        new Employee("МосКВин", "иван", "Геннадьевич", 5, 180000),
                        new Employee("Sidorov", "ivan", "Ivanovich", 5, 150000),
                        new Employee("Головин", "Алексей", "Петрович", 5, 100000)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForGetAllEmployeesByDepartment")
    void getAllEmployeesByDepartment(int depId, List <Employee> expected) {
        assertThat(departmentService.getAllEmployeesByDepartment(depId)).containsExactlyElementsOf(expected);
    }

    public static Stream<Arguments> provideParamsForMaxSalary() {
        return Stream.of(
                Arguments.of(5, new Employee("МосКВин", "иван", "Геннадьевич", 5, 180000)),
                Arguments.of(2, new Employee("Коробов", "Сергей", "Иванович", 2, 125000))
        );
    }

    public static Stream<Arguments> provideParamsForMinSalary() {
        return Stream.of(
                Arguments.of(5, new Employee("Головин", "Алексей", "Петрович", 5, 100000)),
                Arguments.of(2, new Employee("Sidorkin", "petr", "ivaNovich", 2, 45000))
        );
    }

    public static Stream<Arguments> provideParamsForGetAllEmployeesByDepartment() {
        return Stream.of(
                Arguments.of(2, List.of(
                        new Employee("Sidorkin", "petr", "ivaNovich", 2, 45000),
                        new Employee("Коробов", "Сергей", "Иванович", 2, 125000))),
                Arguments.of(5, List.of(
                        new Employee("МосКВин", "иван", "Геннадьевич", 5, 180000),
                        new Employee("Sidorov", "ivan", "Ivanovich", 5, 150000),
                        new Employee("Головин", "Алексей", "Петрович", 5, 100000))),
                Arguments.of(3, Collections.emptyList())
        );
    }
}