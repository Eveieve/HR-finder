package dao;

import dto.Departments;
import dto.EventBackup;
import util.utildemo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentDaoImpl implements DepartmentDao {

    @Override
    public Optional<Departments> addDepartments(Departments departments) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = utildemo.getConnection();
            String insertSql = "INSERT INTO departments VALUES (?,?,?,?)";
            pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, departments.getDepartment_id());
            pstmt.setString(2, departments.getDepartment_name());
            Integer manager_id = departments.getManager_id();
            if (manager_id == null) {
                pstmt.setNull(3, Types.INTEGER);
            } else {
                pstmt.setInt(3, manager_id);
            }

            Integer location_id = departments.getLocation_id();
            if (location_id == null) {
                pstmt.setNull(4, Types.INTEGER);
            } else {
                pstmt.setInt(4, location_id);
            }

            int cnt = pstmt.executeUpdate();
            if (cnt > 0) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String selectSql = "SELECT * FROM departments WHERE department_id = ?";
                pstmt = conn.prepareStatement(selectSql);
                pstmt.setInt(1, departments.getDepartment_id());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    Integer managerId = (Integer) rs.getObject("manager_id");
                    Integer locationId = (Integer) rs.getObject("location_id");
                    Departments inserted = Departments.builder()
                            .department_id(rs.getInt("department_id"))
                            .department_name(rs.getString("department_name"))
                            .manager_id(managerId)
                            .location_id(locationId)
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
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public int countEmployeesByDepartment(int department_id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int count = -1;
        try {
            conn = utildemo.getConnection();
            String sql = "SELECT COUNT(*) AS cnt FROM employees WHERE department_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, department_id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("cnt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

    @Override
    public int countDepartmentId(int department_id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int count = -1;
        try {
            conn = utildemo.getConnection();
            String sql = "SELECT COUNT(*) AS cnt FROM departments WHERE department_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, department_id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("cnt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

    @Override
    public Optional<Departments> deleteDepartments(int department_id) {
        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement deleteStmt = null;
        ResultSet rs = null;
        try {
            conn = utildemo.getConnection();
            String selectSql = "SELECT * FROM departments WHERE department_id = ?";
            selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setInt(1, department_id);
            rs = selectStmt.executeQuery();
            if (rs.next()) {
                Integer newManagerId = null;
                Object obj = rs.getObject("manager_id");
                if (obj != null) {
                    if (obj instanceof Long) {
                        newManagerId = ((Long) obj).intValue();
                    } else if (obj instanceof Integer) {
                        newManagerId = (Integer) obj;
                    }
                }
                Integer newLocationId = null;
                obj = rs.getObject("manager_id");
                if (obj != null) {
                    if (obj instanceof Long) {
                        newManagerId = ((Long) obj).intValue();
                    } else if (obj instanceof Integer) {
                        newManagerId = (Integer) obj;
                    }
                }

                Departments dept = Departments.builder()
                        .department_id(rs.getInt("department_id"))
                        .department_name(rs.getString("department_name"))
                        .manager_id(newManagerId)
                        .location_id(newLocationId)
                        .build();

                String deleteSql = "DELETE FROM departments WHERE department_id = ?";
                deleteStmt = conn.prepareStatement(deleteSql);
                deleteStmt.setInt(1, department_id);
                int cnt = deleteStmt.executeUpdate();
                if (cnt > 0) {
                    System.out.println("Successfully deleted department with department_id: " + department_id);
                    return Optional.of(dept);
                } else {
                    System.out.println("Failed to delete department with department_id: " + department_id);
                }
            } else {
                System.out.println("No department found with department_id: " + department_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (selectStmt != null) {
                try {
                    selectStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (deleteStmt != null) {
                try {
                    deleteStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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
                    Integer newManagerId = null;
                    Object obj = rs.getObject("manager_id");
                    if (obj != null) {
                        if (obj instanceof Long) {
                            newManagerId = ((Long) obj).intValue();
                        } else if (obj instanceof Integer) {
                            newManagerId = (Integer) obj;
                        }
                    }

                    Departments department = Departments.builder()
                            .department_id(rs.getInt("department_id"))
                            .department_name(rs.getString("department_name"))
                            .manager_id(newManagerId)
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (updateStmt != null) {
                try {
                    updateStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (selectStmt != null) {
                try {
                    selectStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return Optional.empty();
    }

    @Override
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
                    Integer managerId = (Integer) rs.getObject("manager_id");
                    Integer locationId = (Integer) rs.getObject("location_id");
                    Departments department = Departments.builder()
                            .department_id(rs.getInt("department_id"))
                            .department_name(rs.getString("department_name"))
                            .manager_id(managerId)
                            .location_id(locationId)
                            .build();
                    return Optional.of(department);
                }
            } else {
                System.out.println("No department record updated for department_id: " + department_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (updateStmt != null) {
                try {
                    updateStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (selectStmt != null) {
                try {
                    selectStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return Optional.empty();
    }

/*
-- 제약 조건 확인하는 법
SELECT
    CONSTRAINT_NAME,
    TABLE_NAME,
    COLUMN_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM information_schema.KEY_COLUMN_USAGE
WHERE TABLE_SCHEMA = 'hr'
  AND TABLE_NAME = 'employees'
  AND REFERENCED_TABLE_NAME IS NOT NULL;
 */
/*
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

}