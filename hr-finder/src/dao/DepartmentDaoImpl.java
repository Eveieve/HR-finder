package dao;

import dto.Departments;
import util.utildemo;

import java.sql.*;
import java.util.Optional;

public class DepartmentDaoImpl {
    Connection conn = null;
    PreparedStatement pstmt = null;

    public Optional<Departments> addDepartments(Departments departments) {

        conn = utildemo.getConnection();
        ResultSet rs = null;
        try {
            String insertSql = "INSERT INTO departments " +
                    "VALUES (?,?,?,?)";
            pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, departments.getDepartment_id());
            pstmt.setString(2, departments.getDepartment_name());
            pstmt.setInt(3, departments.getManager_id());
            pstmt.setInt(4, departments.getLocation_id());

            int cnt = pstmt.executeUpdate();
            if (cnt > 0) {
                if(pstmt != null) {
                    try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
                }
                String selectSql = "SELECT * FROM departments WHERE department_id = ?";
                pstmt = conn.prepareStatement(selectSql);
                pstmt.setInt(1, departments.getDepartment_id());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    Departments inserted = Departments.builder()
                            .department_id(rs.getInt("department_id"))
                            .department_name(rs.getString("department_name"))
                            .manager_id(rs.getInt("manager_id"))
                            .location_id(rs.getInt("location_id"))
                            .build();
                    return Optional.of(inserted);
                }
            } else {
                System.out.println("Failed to add departments information for department_id: " + departments.getDepartment_id());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (pstmt != null) {
                try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return Optional.empty();
    }

    public int countEmployeesByDepartment(int department_id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = utildemo.getConnection();
            String sql = "SELECT COUNT(*) AS cnt FROM employees WHERE department_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, department_id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cnt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public Optional<Departments> deleteDepartment(int department_id) {
        Connection conn = null;
        PreparedStatement deleteStmt = null;
        try {
            conn = utildemo.getConnection();
            String deleteSql = "DELETE FROM departments WHERE department_id = ?";
            deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, department_id);
            int cnt = deleteStmt.executeUpdate();
            if (cnt > 0) {
                System.out.println("Successfully deleted department with department_id: " + department_id);
                return Optional.empty();
            } else {
                System.out.println("No department found with department_id: " + department_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (deleteStmt != null) deleteStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }




    public Optional<Departments> updateDepartmentsManager_id(int department_id, int manager_id) {
        Connection conn = null;
        PreparedStatement updateStmt = null;
        PreparedStatement selectStmt = null;
        ResultSet rs = null;
        try {
            conn = utildemo.getConnection();
            String updateSql = "UPDATE departments SET manager_id = ? WHERE department_id = ?";
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, manager_id);
            updateStmt.setInt(2, department_id);

            int cnt = updateStmt.executeUpdate();
            if (cnt > 0) {
                System.out.println("Successfully updated departments with manager_id: " + manager_id);

                String selectSql = "SELECT * FROM departments WHERE department_id = ?";
                selectStmt = conn.prepareStatement(selectSql);
                selectStmt.setInt(1, department_id);
                rs = selectStmt.executeQuery();
                if (rs.next()) {
                    Departments department = Departments.builder()
                            .department_id(rs.getInt("department_id"))
                            .department_name(rs.getString("department_name"))
                            .manager_id(rs.getInt("manager_id"))
                            .location_id(rs.getInt("location_id"))
                            .build();
                    return Optional.of(department);
                }
            } else {
                System.out.println("No department record updated for department_id: " + department_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (updateStmt != null) updateStmt.close();
                if (selectStmt != null) selectStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public Optional<Departments> updateDepartments_name(int department_id, String department_name) {
        Connection conn = null;
        PreparedStatement updateStmt = null;
        PreparedStatement selectStmt = null;
        ResultSet rs = null;
        try {
            conn = utildemo.getConnection();
            String updateSql = "UPDATE departments SET department_name = ? WHERE department_id = ?";
            updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, department_name);
            updateStmt.setInt(2, department_id);

            int cnt = updateStmt.executeUpdate();
            if (cnt > 0) {
                System.out.println("Successfully updated departments with department_name: " + department_name);
                String selectSql = "SELECT * FROM departments WHERE department_id = ?";
                selectStmt = conn.prepareStatement(selectSql);
                selectStmt.setInt(1, department_id);
                rs = selectStmt.executeQuery();
                if (rs.next()) {
                    Departments department = Departments.builder()
                            .department_id(rs.getInt("department_id"))
                            .department_name(rs.getString("department_name"))
                            .manager_id(rs.getInt("manager_id"))
                            .location_id(rs.getInt("location_id"))
                            .build();
                    return Optional.of(department);
                }
            } else {
                System.out.println("No department record updated for department_id: " + department_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (updateStmt != null) updateStmt.close();
                if (selectStmt != null) selectStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
}

/* 트리거 혹시 몰라서 남겨두기용
DROP TRIGGER IF EXISTS trg_departments_manager_update;
DELIMITER $$
CREATE TRIGGER trg_departments_manager_update
    AFTER UPDATE ON departments
    FOR EACH ROW
BEGIN
    IF NEW.manager_id <> OLD.manager_id THEN
        UPDATE employees
        SET manager_id = NEW.manager_id
        WHERE department_id = NEW.department_id;
    END IF;
END $$
DELIMITER ;
 */