package HRmanager0303.service;

import HRmanager0303.dao.EmployeeDaoImpl;
import HRmanager0303.dto.Employees;
import java.util.*;


public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

    //사원 번호를 기준으로 검색
    public List<Employees> searchByEmpId(int employee_id) {

        Optional<List<Employees>> searchList = employeeDao.findEmployee("employee_id", employee_id);
        if (searchList.isEmpty()) {
            System.out.println("Employee not found" + employee_id);
        } else {
            searchSubMenu(searchList.get());
        }
        return searchList.orElse(new ArrayList<>());
    }

    //이름으로 검색 - last name 검색
    public List<Employees>  searchByLastname(String Last_name) {

        Optional<List<Employees>> searchList = employeeDao.findEmployee("last_name", Last_name);

        if (searchList.isEmpty()) {
            System.out.println("Employee not found" + Last_name);
        } else {
            searchSubMenu(searchList.get());
        }
        return searchList.orElse(new ArrayList<>());
    }

    //이름 검색 - first name 검색
    public List<Employees> searchByFirstname(String First_name) {

        Optional<List<Employees>>  searchList = employeeDao.findEmployee("first_name", First_name);
        if (searchList.isEmpty()) {
            System.out.println("Employee not found" + First_name);
        } else {
            searchSubMenu(searchList.get());
        }
        return searchList.orElse(new ArrayList<>());
    }

    //직업 검색
    public List<Employees> searchByJobId(String job_id) {

        Optional<List<Employees>>  searchList = employeeDao.findEmployee("job_id", job_id);
        if (searchList.isEmpty()) {
            System.out.println("Employee not found " + job_id);
        } else {
            searchSubMenu(searchList.get());
        }
        return searchList.orElse(new ArrayList<>());
    }


    //고용일/근속기간 검색 - 고용일 검색
    public List<Employees> searchByHireDate(Date hire_date) {
        // java.util.Date를 java.sql.Date로 변환
        java.sql.Date sqlHireDate = new java.sql.Date(hire_date.getTime());

        Optional<List<Employees>>  searchList = employeeDao.findEmployee("hire_date", sqlHireDate);
        if (searchList.isEmpty()) {
            System.out.println("Employee not found"  + hire_date);
        } else {
            searchSubMenu(searchList.get());
        }
        return searchList.orElse(new ArrayList<>());

    }

    //고용일/근속기간 검색 - 근무기간 검색 : job_history
    public List<Employees> searchByEmploymentDuration(Date startDate, Date endDate) {

        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

        Optional<List<Employees>> searchList = employeeDao.findJobHistory(sqlStartDate, sqlEndDate);
        if (searchList.isEmpty()) {
            System.out.println("Employee not found"  );
        } else {
            searchSubMenu(searchList.get());
        }
        return searchList.orElse(new ArrayList<>());
    }


    //subMenu : 직원수만 확인 하거나 해당 직원의 정보를 조회할 수 있다.
    public List<Employees> searchSubMenu(List<Employees> searchList) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Count  2. All Load");
            String input = sc.nextLine();

            try {
                int subchoice = Integer.parseInt(input);
                switch (subchoice) {
                    case 1:
                        System.out.println("Number of applicable employees : " + searchList.size());

                        break;
                    case 2:
                        System.out.println("result applicable employees ");
                        for (Employees employees : searchList) {
                            System.out.println(employees);
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


    //sort : 사원번호를 기준으로 정렬
    public List<Employees> sortByEmpId() {
        Optional<List<Employees>> sortEmployeeList = employeeDao.loadEmployee();
        sortSubmenu(sortEmployeeList.get(),Comparator.comparing(Employees::getEmployee_id));
        for(Employees employee : sortEmployeeList.get()){
            System.out.println(employee);
        }
        return sortEmployeeList.orElse(new ArrayList<>());
    }

    @Override
    public List<Employees> ssortByName() {
        return List.of();
    }

    @Override
    public List<Employees> sortByEmploymentDuration() {
        return List.of();
    }

    //sort : 이름 기준으로 정렬
    public List<Employees> sortByName(){
        Optional<List<Employees>> sortEmployeeList = employeeDao.loadEmployee();
        sortSubmenu(sortEmployeeList.get(),Comparator.comparing(Employees::getFirst_name)
                .thenComparing(Employees::getLast_name));
        for(Employees employee : sortEmployeeList.get()){
            System.out.println(employee);
        }
        return sortEmployeeList.orElse(new ArrayList<>());
    }
    //sort : 입사일 기준으로 정렬
    public List<Employees> sortByJHireDate(){
        Optional<List<Employees>> sortEmployeeList = employeeDao.loadEmployee();
        sortSubmenu(sortEmployeeList.get(),Comparator.comparing(Employees::getHire_date));
        for(Employees employee : sortEmployeeList.get()){
            System.out.println(employee);
        }
        return sortEmployeeList.orElse(new ArrayList<>());
    }
    //sort : 사원번호를 기준으로 정렬
    public List<Employees> sortByJobId(){
        Optional<List<Employees>> sortEmployeeList = employeeDao.loadEmployee();
        sortSubmenu(sortEmployeeList.get(),Comparator.comparing(Employees::getJob_id));
        for(Employees employee : sortEmployeeList.get()){
            System.out.println(employee);
        }
        return sortEmployeeList.orElse(new ArrayList<>());
    }

    @Override
    public List<Employees> sortSubmenu() {
        return List.of();
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


