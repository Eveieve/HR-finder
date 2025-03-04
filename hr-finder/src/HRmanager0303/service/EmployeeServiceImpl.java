package HRmanager0303.service;

import HRmanager0303.dao.EmployeeDaoImpl;
import HRmanager0303.dto.Employees;
import java.util.*;


public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
    @Override
    public List<Employees> addEmployee(Employees employee) {
        var insertedEmployee = employeeDao.addEmployee(employee);
        insertedEmployee.ifPresentOrElse(
                e -> System.out.println("Successfully added employee: " + e),
                () -> System.out.println("Failed to add employee with employee_id: " + employee.getEmployee_id())
        );
        return insertedEmployee
                .map(e -> new ArrayList<>(Collections.singletonList(e)))
                .orElse(new ArrayList<>());
    }

    @Override
    public List<Employees> deleteEmployee(int employeeId) {
        var deletedEmployee = employeeDao.deleteEmployee(employeeId);
        deletedEmployee.ifPresentOrElse(
                e -> System.out.println("Successfully deleted employee: " + e),
                () -> System.out.println("Failed to delete employee with employee_id: " + employeeId)
        );
        return deletedEmployee
                .map(e -> new ArrayList<>(Collections.singletonList(e)))
                .orElse(new ArrayList<>());
    }

    @Override
    public List<Employees> updateEmployee(Employees employee) {
        var updatedEmployee = employeeDao.updateEmployee(employee);
        updatedEmployee.ifPresentOrElse(
                e -> System.out.println("Successfully updated employee: " + e),
                () -> System.out.println("Failed to update employee with employee_id: " + employee.getEmployee_id())
        );
        return updatedEmployee
                .map(e -> new ArrayList<>(Collections.singletonList(e)))
                .orElse(new ArrayList<>());
    }

    @Override
    public List<Employees> updateName(String oldFullName, String newFirstName, String newLastName) {
        var updatedEmployees = employeeDao.updateName(oldFullName, newFirstName, newLastName);
        updatedEmployees.ifPresentOrElse(
                list -> {
                    System.out.println("Successfully updated employee names:");
                    list.forEach(System.out::println);
                },
                () -> System.out.println("Failed to update employee name for full name: " + oldFullName)
        );
        return updatedEmployees.orElse(Collections.emptyList());
    }

    @Override
    public List<Employees> updateByChoice(String fieldToUpdate, String oldValue, String newValue) {
        var updatedEmployees = employeeDao.updateByChoice(fieldToUpdate, oldValue, newValue);
        updatedEmployees.ifPresentOrElse(
                list -> {
                    System.out.println("Successfully updated " + fieldToUpdate + ":");
                    list.forEach(System.out::println);
                },
                () -> System.out.println("Failed to update " + fieldToUpdate + " from " + oldValue + " to " + newValue)
        );
        return updatedEmployees.orElse(Collections.emptyList());
    }

    //sort  sub menu : 오름차순 / 내림차순 선택
    public List<Employees> sortSubmenu(List<Employees> sortList , Comparator<Employees> comparator){
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Ascending  2. Descending");
            String input = sc.nextLine();

            try {
                int subchoice = Integer.parseInt(input);
                switch (subchoice) {
                    case 1:
                        System.out.println("Sort in ascending order by :" );
                        sortList.sort(comparator);
                        break;
                    case 2:
                        System.out.println("Sort in descending order by :");
                        sortList.sort(comparator.reversed());
                        break;
                    default:
                        System.out.println("Please select again, 1 or 2");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            }
        }
        return sortList;
    }



}


