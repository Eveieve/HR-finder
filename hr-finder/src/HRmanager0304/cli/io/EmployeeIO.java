package HRmanager0304.cli.io;

import HRmanager0304.util.validation.ConsoleInputValidator;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Scanner;

public class EmployeeIO {
    ConsoleInputValidator validator = new ConsoleInputValidator();
    // EmployeeIO에서 Scanner도 사용할 수 있음 (validator 내부 Scanner와 별개)
    private Scanner scanner = new Scanner(System.in);

    public int readEmployeeId() {
        return validator.readValidatedInt("employee_id(int) : ", 0, 10, "Please Check Employee ID");
    }

    public String readString() {
        return scanner.nextLine();
    }

    public Integer readInt() {
        return Integer.parseInt(scanner.nextLine());
    }

    public String readFirstName() {
        return validator.readValidatedVarchar("first_name(max: 20) : ", 20, "Please Check First Name");
    }

    public String readLastName() {
        return validator.readValidatedVarchar("last_name(max: 25) : ", 25, "Please Check Last Name");
    }

    public String readEmail() {
        return validator.readValidatedVarchar("email(max: 25) : ", 25, "Please Check Email");
    }

    public Date readHireDate() {
        System.out.print("날짜를 입력하세요 (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
        try {
            // 간단하게 java.sql.Date.valueOf() 사용 (입력 형식이 정확해야 함)
            return Date.valueOf(dateString);
        } catch (IllegalArgumentException e) {
            // 형식이 틀리면 재입력하도록 예외 처리 (혹은 RuntimeException 발생)
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return readHireDate();
        }
    }

    public String readJobId() {
        return validator.readValidatedVarchar("job_id(max: 10) : ", 10, "Please Check Job ID");
    }

    public BigDecimal readSalary() {
        return validator.readValidatedBigDecimal("salary : ", new BigDecimal("0.00"), new BigDecimal("999999.99"), 2, "Please Check Salary");
    }

    public BigDecimal readCommissionPct() {
        return validator.readValidatedBigDecimal("commission_pct : ", new BigDecimal("0.00"), new BigDecimal("99.99"), 2, "Please Check Commission Percent");
    }

    public int readManagerId() {
        return validator.readValidatedInt("manager_id(int) : ", 0, 10, "Please Check Manager ID");
    }

    public int readDepartmentId() {
        return validator.readValidatedInt("department_id(int) : ", 0, 10, "Please Check Department ID");
    }
}
