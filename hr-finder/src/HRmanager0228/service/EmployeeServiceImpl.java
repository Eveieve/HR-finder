package HRmanager0228.service;

import HRmanager0228.dto.Employees;
import HRmanager0228.util.utildemo;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class EmployeeServiceImpl implements EmployeeService {
    Scanner scan = new Scanner(System.in);
    private static final String URL = "jdbc:mysql://localhost:3306/ssgdb?serverTimezone=Asia/Seoul";
    private static final String USERNAME = "ssg";
    private static final String PASSWORD = "ssg";

    @Override
    public void addEmployees(List<Employees> addEmployees) {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO employees (employee_id, first_name, last_name, email, phone_number, hire_date, job_id, salary, commission, manager_id, department_id)" +
                    "VALUES(?,?,?,?,?,now(),?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            for (Employees employee : addEmployees) {
                pstmt.setString(1, String.valueOf(employee.getEmployee_id()));
                pstmt.setString(2, employee.getFirst_name());
                pstmt.setString(3, employee.getLast_name());
                pstmt.setString(4, employee.getEmail());
                pstmt.setString(5, employee.getPhone_number());
                pstmt.setString(6, employee.getJob_id());
                pstmt.setBigDecimal(7, employee.getSalary());
                pstmt.setBigDecimal(8, employee.getCommission());
                pstmt.setString(9, String.valueOf(employee.getManager_id()));
                pstmt.setInt(10, employee.getDepartment_id());


                int cnt = pstmt.executeUpdate();
                if (cnt > 0) {
                    System.out.println("Successfully added employee information");
                } else {
                    System.out.println("Failed to add employee information");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmployees(List<Employees> deleteEmployees) {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM employees WHERE userid = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (Employees employee : deleteEmployees) {
                pstmt.setString(1, String.valueOf(employee.getEmployee_id()));


                int cnt = pstmt.executeUpdate();
                if (cnt > 0) {
                    System.out.println("Successfully deleted employee information");
                } else {
                    System.out.println("Failed to delete employee information");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateAll(List<Employees> updateAll) {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, phone_number = ?, hire_date = ?, job_id = ?, salary = ?, commission = ?, manager_id = ?, department_id = ? WHERE employee_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (Employees employee : updateAll) {
                pstmt.setString(1, employee.getFirst_name());
                pstmt.setString(2, employee.getLast_name());
                pstmt.setString(3, employee.getEmail());
                pstmt.setString(4, employee.getPhone_number());
                pstmt.setDate(5, employee.getHire_date());
                pstmt.setString(6, employee.getJob_id());
                pstmt.setBigDecimal(7, employee.getSalary());
                pstmt.setBigDecimal(8, employee.getCommission());
                pstmt.setInt(9, employee.getManager_id());
                pstmt.setInt(10, employee.getDepartment_id());
                pstmt.setInt(11, employee.getEmployee_id());


                int cnt = pstmt.executeUpdate();
                if (cnt > 0) {
                    System.out.println("Successfully updated employee information");
                } else {
                    System.out.println("Failed to update employee information");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateEmpId(List<Employees> updateEmpId) {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "UPDATE employees SET employee_id = ? WHERE employee_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (Employees employee : updateEmpId) {
                pstmt.setInt(1, employee.getEmployee_id());


                int cnt = pstmt.executeUpdate();
                if (cnt > 0) {
                    System.out.println("Successfully updated employee_id information");
                } else {
                    System.out.println("Failed to update employee_id information");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateName(List<Employees> updateName) {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "UPDATE employees SET first_name = ?, last_name = ? WHERE CONCAT(first_name, ' ', last_name) = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (Employees employee : updateName) {
                pstmt.setString(1, employee.getFirst_name());
                pstmt.setString(2, employee.getLast_name());
                pstmt.setString(3, employee.getFirst_name() + " " + employee.getLast_name());

                int cnt = pstmt.executeUpdate();
                if (cnt > 0) {
                    System.out.println("Successfully updated employee_name information");
                } else {
                    System.out.println("Failed to update employee_name information");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateByChoice(List<Employees> updateByChoice) {

        List<String> allowedFields = Arrays.asList("first_name", "last_name", "email", "phone_number", "job_id", "salary", "commission_pct", "manager_id", "department_id");

        for (Employees employee : updateByChoice) {
            String fieldToUpdate = employee.getUpdateField();
            String newValueStr = employee.getNewValue();
            String oldValueStr = employee.getOldValue();

            if (!allowedFields.contains(fieldToUpdate)) {
                System.out.println("Invalid update field: " + fieldToUpdate);
                continue;
            }

            String sql = "UPDATE employees SET " + fieldToUpdate + " = ? WHERE " + fieldToUpdate + " = ?";

            try {
                Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql);

                if (fieldToUpdate.equals("salary") || fieldToUpdate.equals("commission_pct")) {
                    BigDecimal newValue = new BigDecimal(newValueStr);
                    BigDecimal oldValue = new BigDecimal(oldValueStr);
                    pstmt.setBigDecimal(1, newValue);
                    pstmt.setBigDecimal(2, oldValue);
                } else if (fieldToUpdate.equals("manager_id") || fieldToUpdate.equals("department_id")) {
                    int newValue = Integer.parseInt(newValueStr);
                    int oldValue = Integer.parseInt(oldValueStr);
                    pstmt.setInt(1, newValue);
                    pstmt.setInt(2, oldValue);
                } else {
                    pstmt.setString(1, newValueStr);
                    pstmt.setString(2, oldValueStr);
                }

                int cnt = pstmt.executeUpdate();
                if (cnt > 0) {
                    System.out.println("Employee의 " + fieldToUpdate + "가 성공적으로 업데이트되었습니다.");
                } else {
                    System.out.println("Employee의 " + fieldToUpdate + " 업데이트 실패.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private int getMemberCount(String memId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int count = 0;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "" +
                    "SELECT COUNT(userid) FROM users WHERE userid = ?";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, memId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
                System.out.println("해당 회원의 ID 개수 : " + count);
            } else {
                System.out.println("회원 ID가 존재하지 않습니다");
            }

        } catch (SQLException e) {
            count = 0;
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            }
        }

        return count;

    }
}


