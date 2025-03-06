package cli.io;

import util.validation.ConsoleInputValidator;

public class DepartmentIO {
    ConsoleInputValidator validator = new ConsoleInputValidator();

    public int readDepartmentId() {
        return validator.readValidatedIntNoMax("department_id(int) : ",0,"Please Check Department ID");
    }

    public String readDepartmentName() {
        return validator.readValidatedVarchar("department_name (max:30) : ",30,"Pleas Check Department Name");
    }

    public int readDepartmentManagerId() {
        return validator.readValidatedIntNoMax("department_manager_id(int) : ",0,"Please Check Department Manager ID");
    }

    public int readDepartmentLocation() {
        return validator.readValidatedIntNoMax("department_location (int) : ",0,"Pleas Check Department Location");
    }
}
