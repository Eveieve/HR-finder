package HRmanager0303.service;

import HRmanager0303.dto.Employees;

import java.util.Date;
import java.util.List;

public interface EmployeeService {
    List<Employees> addEmployee(Employees employee);
    List<Employees> deleteEmployee(int employeeId);
    List<Employees> updateEmployee(Employees employee);
    List<Employees> updateName(String oldFullName, String newFirstName, String newLastName);
    List<Employees> updateByChoice(String fieldToUpdate, String oldValue, String newValue);
}
