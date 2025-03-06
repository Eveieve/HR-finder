package cli.controller;

import cli.io.EmployeeIO;
import dto.Employees;
import service.EmployeeService;
import service.EmployeeServiceImpl;
import service.LoginDemoImpl;

import java.util.List;
import java.util.Scanner;

public class MainController {
    private Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        MainController controller = new MainController();
        controller.run();
    }

    public void run() {
        boolean isAdmin = LoginDemoImpl.login();

        if (isAdmin) {
            adminMainMenu();
        } else {
            userMainMenu();
        }
    }

    private void adminMainMenu() {
        DepartmentController Dcontroller = new DepartmentController();
        EmployeeController Econtroller = new EmployeeController();
        SalaryController Scontroller = new SalaryController();

        boolean exitProgram = false;

        while (!exitProgram) {
            System.out.println("========== Main Menu ==========");
            System.out.println("1. Departments");
            System.out.println("2. Employees");
            System.out.println("3. Salary");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            int mainChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainChoice) {
                case 1:
                    Dcontroller.run();
                    break;
                case 2:
                    Econtroller.run();
                    break;
                case 3:
                    Scontroller.run();
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
    }

    private void userMainMenu() {
        EmployeeController Econtroller = new EmployeeController();
        boolean exitProgram = false;

        while (!exitProgram) {
            System.out.println("========== Main Menu ==========");
            System.out.println("1. Employees");
            System.out.println("2. Salary");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            int mainChoice = scanner.nextInt();
            scanner.nextLine();

            switch (mainChoice) {
                case 1:
                    System.out.println("========== Employees Submenu ==========");
                    System.out.println("1. Search");
                    System.out.println("2. Sort");
                    System.out.print("Select an option: ");
                    int empChoice = scanner.nextInt();
                    scanner.nextLine();
                    switch (empChoice) {
                        case 1:
                            Econtroller.displaySearchMenu(scanner);
                            break;
                        case 2:
                            Econtroller.displaySortMenu(scanner);
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                    break;
                case 2:

                    break;
                case 3:
                    System.out.println("Exiting program. Goodbye!");
                    exitProgram = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
    }
}
