package dao;

import dto.Departments;

import java.util.Optional;

public interface DepartmentDao {
    Optional<Departments> addDepartments(Departments departments);
    Optional<Departments> deleteDepartments(int departmentsId);
    Optional<Departments> updateDepartments(Departments departments);
}
