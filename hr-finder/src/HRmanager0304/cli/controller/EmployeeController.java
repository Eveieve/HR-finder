package HRmanager0304.cli.controller;

import HRmanager0304.cli.io.EmployeeIO;
import HRmanager0304.dto.Employees;
import HRmanager0304.service.EmployeeService;
import HRmanager0304.service.EmployeeServiceImpl;
import java.util.Scanner;

public class EmployeeController {

    private EmployeeService employeeService = new EmployeeServiceImpl();
    private EmployeeIO employeeIO = new EmployeeIO();

    public static void main(String[] args) {
        EmployeeController controller = new EmployeeController();
        controller.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean exitProgram = false;

        while (!exitProgram) {
            System.out.println("========== Main Menu ==========");
            System.out.println("1. Add / Delete");
            System.out.println("2. Update");
            System.out.println("3. Search");
            System.out.println("4. Sort");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            int mainChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainChoice) {
                case 1:
                    displayAddDeleteMenu(scanner);
                    break;
                case 2:
                    displayUpdateMenu(scanner);
                    break;
                case 3:
                    displaySearchMenu(scanner);
                    break;
                case 4:
                    displaySortMenu(scanner);
                    break;
                case 5:
                    System.out.println("Exiting program. Goodbye!");
                    exitProgram = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private void displayAddDeleteMenu(Scanner scanner) {
        System.out.println("------ Add / Delete Menu ------");
        System.out.println("1. Add Employee Information");
        System.out.println("2. Delete Employee Information");
        System.out.print("Select an option: ");

        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                System.out.println("[Add] Employee Information selected.");
                addEmployee();
                break;
            case 2:
                System.out.println("[Delete] Employee Information selected.");
                employeeService.deleteEmployee(employeeIO.readEmployeeId());
                break;
            default:
                System.out.println("Invalid option in Add/Delete menu.");
        }
    }

    public void addEmployee() {
        int employee_id = employeeIO.readEmployeeId();
        String first_name = employeeIO.readFirstName();
        String last_name = employeeIO.readLastName();
        String email = employeeIO.readEmail();
        java.sql.Date hire_date = employeeIO.readHireDate();
        String job_id = employeeIO.readJobId();
        java.math.BigDecimal salary = employeeIO.readSalary();
        java.math.BigDecimal commission_pct = employeeIO.readCommissionPct();
        int manager_id = employeeIO.readManagerId();
        int department_id = employeeIO.readDepartmentId();

        Employees employee = Employees.builder()
                .employee_id(employee_id)
                .first_name(first_name)
                .last_name(last_name)
                .email(email)
                .hire_date(hire_date)
                .job_id(job_id)
                .salary(salary)
                .commission(commission_pct)
                .manager_id(manager_id)
                .department_id(department_id)
                .build();

        employeeService.addEmployee(employee);
    }

    public void updateEmployee() {
        int employee_id = employeeIO.readEmployeeId();
        String first_name = employeeIO.readFirstName();
        String last_name = employeeIO.readLastName();
        String email = employeeIO.readEmail();
        java.sql.Date hire_date = employeeIO.readHireDate();
        String job_id = employeeIO.readJobId();
        java.math.BigDecimal salary = employeeIO.readSalary();
        java.math.BigDecimal commission_pct = employeeIO.readCommissionPct();
        int manager_id = employeeIO.readManagerId();
        int department_id = employeeIO.readDepartmentId();

        Employees employee = Employees.builder()
                .employee_id(employee_id)
                .first_name(first_name)
                .last_name(last_name)
                .email(email)
                .hire_date(hire_date)
                .job_id(job_id)
                .salary(salary)
                .commission(commission_pct)
                .manager_id(manager_id)
                .department_id(department_id)
                .build();

        employeeService.updateEmployee(employee);
    }

    private void displayUpdateMenu(Scanner scanner) {
        System.out.println("------ Update Menu ------");
        System.out.println("1. Modify All");
        System.out.println("2. Modify Name");
        System.out.println("3. Select Content to Modify");
        System.out.print("Select an option: ");

        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                System.out.println("[Update] Modify All selected.");
                updateEmployee();
                break;
            case 2:
                System.out.println("[Update] Modify Name selected.");
                String oldFUllName = employeeIO.readString();
                String newFirstName = employeeIO.readFirstName();
                String newLastName = employeeIO.readLastName();
                employeeService.updateName(oldFUllName, newFirstName, newLastName);
                break;
            case 3:
                System.out.println("[Update] Select Content to Modify selected.");
                String fieldToUpdate = employeeIO.readString();
                String oldValue = employeeIO.readString();
                String newValue = employeeIO.readString();
                employeeService.updateByChoice(fieldToUpdate, newValue, oldValue);
                break;
            default:
                System.out.println("Invalid option in Update menu.");
        }
    }

    private void displaySearchMenu(Scanner scanner) {
        System.out.println("------ Search Menu ------");
        System.out.println("1. Search by Employee ID");
        System.out.println("2. Search by Last Name");
        System.out.println("3. Search by First Name");
        System.out.println("4. Search by Job Title");
        System.out.println("5. Search by Hire Date");
        System.out.println("6. Search by Employment Duration");
        System.out.println("7. Search Summary");
        System.out.print("Select an option: ");

        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                System.out.println("[Search] Search by Employee ID selected.");
                break;
            case 2:
                System.out.println("[Search] Search by Last Name selected.");
                break;
            case 3:
                System.out.println("[Search] Search by First Name selected.");
                break;
            case 4:
                System.out.println("[Search] Search by Job Title selected.");
                break;
            case 5:
                System.out.println("[Search] Search by Hire Date selected.");
                break;
            case 6:
                System.out.println("[Search] Search by Employment Duration selected.");
                break;
            case 7:
                System.out.println("[Search] Search Summary selected.");
                break;
            default:
                System.out.println("Invalid option in Search menu.");
        }
    }

    private void displaySortMenu(Scanner scanner) {
        System.out.println("------ Sort Menu ------");
        System.out.println("1. Sort by Employee ID");
        System.out.println("2. Sort by Name");
        System.out.println("3. Sort by Hire Date");
        System.out.println("4. Sort by Job Title");
        System.out.println("5. Sort Order (Ascending/Descending)");
        System.out.print("Select an option: ");

        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                System.out.println("[Sort] Sort by Employee ID selected.");
                break;
            case 2:
                System.out.println("[Sort] Sort by Name selected.");
                break;
            case 3:
                System.out.println("[Sort] Sort by Hire Date selected.");
                break;
            case 4:
                System.out.println("[Sort] Sort by Job Title selected.");
                break;
            case 5:
                System.out.println("Select sort order:");
                System.out.println("1. Ascending");
                System.out.println("2. Descending");
                System.out.print("Select an option: ");
                int order = scanner.nextInt();
                scanner.nextLine();
                if (order == 1) {
                    System.out.println("Ascending order selected.");
                } else if (order == 2) {
                    System.out.println("Descending order selected.");
                } else {
                    System.out.println("Invalid sort order option.");
                }
                break;
            default:
                System.out.println("Invalid option in Sort menu.");
        }
    }
}
