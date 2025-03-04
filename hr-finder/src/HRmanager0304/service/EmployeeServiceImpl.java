package HRmanager0304.service;

import HRmanager0304.dao.EmployeeDaoImpl;
import HRmanager0304.dto.Employees;
import java.util.*;

import static HRmanager0304.util.validation.ConsoleInputValidator.validateFieldValue;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

    // 사원 번호를 기준으로 검색
    public List<Employees> searchByEmpId(int employee_id) {
        Optional<List<Employees>> searchList = employeeDao.findEmployee("employee_id", employee_id);

        searchList.ifPresentOrElse(
                list -> searchSubMenu(list),
                () -> System.out.println("Employee not found: " + employee_id)
        );
        return searchList.orElse(new ArrayList<>());
    }

    // 이름으로 검색 - last name 검색
    public List<Employees> searchByLastname(String Last_name) {
        Optional<List<Employees>> searchList = employeeDao.findEmployee("last_name", Last_name);

        searchList.ifPresentOrElse(
                list -> searchSubMenu(list),
                () -> System.out.println("Employee not found: " + Last_name)
        );

        return searchList.orElse(new ArrayList<>());
    }

    // 이름 검색 - first name 검색
    public List<Employees> searchByFirstname(String First_name) {
        Optional<List<Employees>> searchList = employeeDao.findEmployee("first_name", First_name);

        searchList.ifPresentOrElse(
                list -> searchSubMenu(list),
                () -> System.out.println("Employee not found: " + First_name)
        );

        return searchList.orElse(new ArrayList<>());
    }

    // 직업 검색
    public List<Employees> searchByJobId(String job_id) {
        Optional<List<Employees>> searchList = employeeDao.findEmployee("job_id", job_id);

        searchList.ifPresentOrElse(
                list -> searchSubMenu(list),
                () -> System.out.println("Employee not found: " + job_id)
        );

        return searchList.orElse(new ArrayList<>());
    }

    // 고용일/근속기간 검색 - 고용일 검색
    public List<Employees> searchByHireDate(Date hire_date) {
        java.sql.Date sqlHireDate = new java.sql.Date(hire_date.getTime());

        Optional<List<Employees>> searchList = employeeDao.findEmployee("hire_date", sqlHireDate);

        searchList.ifPresentOrElse(
                list -> searchSubMenu(list),
                () -> System.out.println("Employee not found: " + hire_date)
        );

        return searchList.orElse(new ArrayList<>());
    }

    // 고용일/근속기간 검색 - 근무기간 검색 : job_history
    public List<Employees> searchByEmploymentDuration(Date startDate, Date endDate) {
        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

        Optional<List<Employees>> searchList = employeeDao.findJobHistory(sqlStartDate, sqlEndDate);

        searchList.ifPresentOrElse(
                list -> searchSubMenu(list),
                () -> System.out.println("Employee not found.")
        );

        return searchList.orElse(new ArrayList<>());
    }

    // subMenu : 직원수만 확인하거나 해당 직원의 정보를 조회할 수 있다.
    public List<Employees> searchSubMenu(List<Employees> searchList) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Count  2. All Load");
            String input = sc.nextLine();

            try {
                int subchoice = Integer.parseInt(input);
                switch (subchoice) {
                    case 1:
                        System.out.println("Number of applicable employees: " + searchList.size());
                        break;
                    case 2:
                        System.out.println("Result applicable employees:");
                        for (Employees employee : searchList) {
                            System.out.println(employee);
                            System.out.println();
                        }
                        break;
                    default:
                        System.out.println("Please select again, 1 or 2");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            }
        }
        return searchList;
    }

    // sort : 사원번호를 기준으로 정렬
    public List<Employees> sortByEmpId() {
        Optional<List<Employees>> sortEmployeeList = employeeDao.loadEmployees();

        sortEmployeeList.ifPresentOrElse(
                list -> sortSubMenu(list, Comparator.comparing(Employees::getEmployee_id)),
                () -> System.out.println("No employees found.")
        );

        sortEmployeeList.ifPresent(list -> list.forEach(System.out::println));
        return sortEmployeeList.orElse(new ArrayList<>());
    }

    // sort : 이름 기준으로 정렬
    public List<Employees> sortByName() {
        Optional<List<Employees>> sortEmployeeList = employeeDao.loadEmployees();

        sortEmployeeList.ifPresentOrElse(
                list -> sortSubMenu(list, Comparator.comparing(Employees::getFirst_name)
                        .thenComparing(Employees::getLast_name)),
                () -> System.out.println("No employees found.")
        );

        sortEmployeeList.ifPresent(list -> list.forEach(System.out::println));
        return sortEmployeeList.orElse(new ArrayList<>());
    }

    // sort : 입사일 기준으로 정렬
    public List<Employees> sortByJHireDate() {
        Optional<List<Employees>> sortEmployeeList = employeeDao.loadEmployees();

        sortEmployeeList.ifPresentOrElse(
                list -> sortSubMenu(list, Comparator.comparing(Employees::getHire_date)),
                () -> System.out.println("No employees found.")
        );

        sortEmployeeList.ifPresent(list -> list.forEach(System.out::println));
        return sortEmployeeList.orElse(new ArrayList<>());
    }

    // sort : 사원번호를 기준으로 정렬
    public List<Employees> sortByJobId() {
        Optional<List<Employees>> sortEmployeeList = employeeDao.loadEmployees();

        sortEmployeeList.ifPresentOrElse(
                list -> sortSubMenu(list, Comparator.comparing(Employees::getJob_id)),
                () -> System.out.println("No employees found.")
        );

        sortEmployeeList.ifPresent(list -> list.forEach(System.out::println));
        return sortEmployeeList.orElse(new ArrayList<>());
    }


    // sort  sub menu : 오름차순 / 내림차순 선택
    public List<Employees> sortSubMenu(List<Employees> sortList, Comparator<Employees> comparator) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Ascending  2. Descending");
            String input = sc.nextLine();

            try {
                int subchoice = Integer.parseInt(input);
                switch (subchoice) {
                    case 1:
                        System.out.println("Sort in ascending order by:");
                        sortList.sort(comparator);
                        break;
                    case 2:
                        System.out.println("Sort in descending order by:");
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
        if (!validateFieldValue(fieldToUpdate, oldValue)) {
            System.out.println("Invalid old value for field: " + fieldToUpdate);
            return Collections.emptyList();
        }
        if (!validateFieldValue(fieldToUpdate, newValue)) {
            System.out.println("Invalid new value for field: " + fieldToUpdate);
            return Collections.emptyList();
        }

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
}
