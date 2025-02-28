package HRmanager0228.service;

import HRmanager0228.dto.Employees;

import java.util.List;

public interface EmployeeService {
    void addEmployees(List<Employees> addEmployees);
    void deleteEmployees(List<Employees> deleteEmployees);
    void updateAll(List<Employees> updateAll);
    void updateEmpId(List<Employees> updateEmpId);
    void updateName(List<Employees> updateName);
    void updateByChoice(List<Employees> updateChoice);
}
