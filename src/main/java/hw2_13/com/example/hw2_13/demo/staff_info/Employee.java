package hw2_13.com.example.hw2_13.demo.staff_info;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class Employee {
    public static int counter;
    //protected int id;
    protected String name;
    protected String surname;
    protected String secondName;
    protected int department;
    protected float salary;

    public Employee(String surname, String name) {
        this.name = name;
        this.surname = surname;
    }

    public Employee(String surname, String name, String secondName, int department, float salary) {
        if (department < 1 || department > 5) {
            throw new RuntimeException("По условиям задачи номер отдела department от 1 до 5");
        }
        if (salary < 0) {
            throw new RuntimeException("ЗП не может быть отрицательной");
        }

        //id = counter++;
        this.surname = StringUtils.capitalize(surname.toLowerCase());
        this.name = StringUtils.capitalize(name.toLowerCase());
        this.secondName = StringUtils.capitalize(secondName.toLowerCase());
        this.department = department;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSecondName() {
        return secondName;
    }

    /*public int getId() {
        return id;
    }*/

    public int getDepartment() {
        return department;
    }

    public float getSalary() {
        return salary;
    }

    public static int getCounter() {
        return counter;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getFullName() {
        return getSurname() + " " + getName() + " " + getSecondName();
    }

    @Override
    public String toString() {
        return getSurname() + " " + getName() + " " + getSecondName() + ". Department: " + getDepartment() +
                ". Salary: " + getSalary();
    }

    public void indexSalary(float percent) {
        setSalary(getSalary() + getSalary()/100f*percent);
    }

    public String getDataWithoutDepartment() {
        return "ФИО: " + getFullName() + " ЗП = " + getSalary();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return department == employee.getDepartment() && Float.compare(employee.getSalary(), salary) == 0
                && Objects.equals(name, employee.getName()) && Objects.equals(surname, employee.getSurname())
                && Objects.equals(secondName, employee.getSecondName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, secondName, department, salary);
    }
}