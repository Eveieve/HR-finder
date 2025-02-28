package HRmanager0228.cli.controller;

import HRmanager0228.dto.Employees;
import HRmanager0228.service.EmployeeService;
import HRmanager0228.service.EmployeeServiceImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeController {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        EmployeeService employeeService = new EmployeeServiceImpl();

        // 사용자 입력을 받아 Employee 객체 생성
        List<Employees> employeesToAdd = new ArrayList<>();
        System.out.println("Enter employee information to add:");

        System.out.print("Employee ID: ");
        String empId = scan.next();
        System.out.print("First Name: ");
        String firstName = scan.next();
        System.out.print("Last Name: ");
        String lastName = scan.next();
        System.out.print("Email: ");
        String email = scan.next();
        System.out.print("Phone Number: ");
        String phone = scan.next();
        Date hireDate = new Date(System.currentTimeMillis());
        System.out.print("Job ID: ");
        String jobId = scan.next();
        System.out.print("Salary: ");
        BigDecimal salary = scan.nextBigDecimal();
        System.out.print("Commission: ");
        BigDecimal commission = scan.nextBigDecimal();
        System.out.print("Manager ID: ");
        String managerId = scan.next();
        System.out.print("Department ID: ");
        int departmentId = scan.nextInt();

        Employees employee = Employees.builder()
                .employee_id(Integer.parseInt(empId))
                .first_name(firstName)
                .last_name(lastName)
                .email(email)
                .phone_number(phone)
                .hire_date(hireDate)
                .job_id(jobId)
                .salary(salary)
                .commission(commission)
                .manager_id(Integer.parseInt(managerId))
                .department_id(departmentId)
                .build();

        employeesToAdd.add(employee);

        employeeService.addEmployees(employeesToAdd);
    }
}

