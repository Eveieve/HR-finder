package service;

import dao.DepartmentDaoImpl;
import dao.EmployeeDaoImpl;
import dto.Departments;
import dto.Employees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentDaoImpl departmentDao = new DepartmentDaoImpl();

    @Override
    public List<Departments> addDepartments(Departments departments) {
        var insertDepartments = departmentDao.addDepartments(departments);
        insertDepartments.ifPresentOrElse(
                e -> System.out.println("Successfully added departments: " + e),
                () -> System.out.println("Failed to add departments with departments_id: " + departments.getDepartment_id())
        );
        return insertDepartments
                .map(e -> new ArrayList<>(Collections.singletonList(e)))
                .orElse(new ArrayList<>());
    }

    @Override
    public List<Departments> deleteDepartmentIfEmpty(int department_id) {
        int employeeCount = departmentDao.countEmployeesByDepartment(department_id);
        if (employeeCount == 0) {
            var deletedDepartment = departmentDao.deleteDepartment(department_id);
            deletedDepartment.ifPresentOrElse(
                    d -> System.out.println("Successfully deleted department: " + d),
                    () -> System.out.println("Failed to delete department with department_id: " + department_id)
            );
            return deletedDepartment
                    .map(d -> new ArrayList<>(Collections.singletonList(d)))
                    .orElse(new ArrayList<>());
        } else {
            System.out.println("Cannot delete department. There are " + employeeCount + " employees in the department.");
            return new ArrayList<>();
        }
    }


    @Override
    public List<Departments> updateDepartmentsManger_id(int department_id, int manager_id) {
        var updatedDepartments = departmentDao.updateDepartmentsManager_id(department_id, manager_id);
        updatedDepartments.ifPresentOrElse(
                e -> System.out.println("Successfully updated departments: " + e),
                () -> System.out.println("Failed to update departments with departments_id: " + department_id)
        );
        return updatedDepartments
                .map(e -> new ArrayList<>(Collections.singletonList(e)))
                .orElse(new ArrayList<>());
    }

    @Override
    public List<Departments> updateDepartments_name(int department_id, String department_name) {
        var updatedDepartments = departmentDao.updateDepartments_name(department_id, department_name);
        updatedDepartments.ifPresentOrElse(
                e -> System.out.println("Successfully updated departments: " + e),
                () -> System.out.println("Failed to update departments with departments_id: " + department_id)
        );
        return updatedDepartments
                .map(e -> new ArrayList<>(Collections.singletonList(e)))
                .orElse(new ArrayList<>());
    }
}
