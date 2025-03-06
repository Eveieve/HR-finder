package cli.controller;

import cli.io.DepartmentIO;
import cli.io.EmployeeIO;
import dto.Departments;
import dto.Employees;
import service.DepartmentService;
import service.DepartmentServiceImpl;
import service.EmployeeService;
import service.EmployeeServiceImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Scanner;

public class DepartmentController {
    private DepartmentService departmentService = new DepartmentServiceImpl();
    private DepartmentIO departmentIO = new DepartmentIO();

    public static void main(String[] args) {
        DepartmentController controller = new DepartmentController();
        controller.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean exitProgram = false;

        while (!exitProgram) {
            System.out.println("========== Main Menu ==========");
            System.out.println("1. Add");
            System.out.println("2. Delete");
            System.out.println("3. Update");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            int mainChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainChoice) {
                case 1:
                    displayAddMenu(scanner);
                    break;
                case 2:
                    displayDeleteMenu(scanner);
                    break;
                case 3:
                    displayUpdateMenu(scanner);
                    break;
                case 4:
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
    private void displayAddMenu(Scanner scanner) {
        System.out.println("------ Add Menu ------");
        addDepartment();
    }

    public void addDepartment() {
        int department_id = departmentIO.readDepartmentId();
        String department_name = departmentIO.readDepartmentName();
        int department_manager_id = departmentIO.readDepartmentManagerId();
        int department_location_id = departmentIO.readDepartmentLocation();

        new Departments();
        Departments departments = Departments.builder()
                .department_id(department_id)
                .department_name(department_name)
                .manager_id(department_manager_id)
                .location_id(department_location_id)
                .build();

        departmentService.addDepartments(departments);
    }

    private void displayDeleteMenu(Scanner scanner) {
        System.out.println("------ Delete Menu ------");
        System.out.print("Please Enter Departments ID : ");
        int department_id = scanner.nextInt();
        scanner.nextLine();
        departmentService.deleteDepartmentIfEmpty(department_id);
    }

    private void displayUpdateMenu(Scanner scanner) {
        System.out.println("------ Update Menu ------");
        System.out.println("Please Enter Department ID : ");
        int department_id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("1. Update manager_id");
        System.out.println("2. Update department_name");
        System.out.print("Select an option: ");

        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                System.out.println("[Update] Update manager_id selected.");
                System.out.println("Please Enter manager_id : ");
                int manager_id = scanner.nextInt();
                scanner.nextLine();
                departmentService.updateDepartmentsManger_id(department_id,manager_id);
                break;
            case 2:
                System.out.println("[Update] Update department_name selected.");
                System.out.println("Please Enter department_name : ");
                String department_name = scanner.nextLine();
                departmentService.updateDepartments_name(department_id,department_name);
                break;
            default:
                System.out.println("Invalid option in Update menu.");
        }
    }
}
