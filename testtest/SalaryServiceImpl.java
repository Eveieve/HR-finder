package test;

import java.util.List;
import java.util.Optional;

public class SalaryServiceImpl {
    private EmployeeDAO dao = new EmployeeDAO();

    // 직원 정보를 Optional로 반환
    public Optional<Employee> findEmployee(int employeeId) {
        return Optional.ofNullable(dao.getEmployeeById(employeeId));
    }

    // 직원의 세전 연봉 계산 (월급 * 12), 직원이 없으면 -1 반환
    public double getEmployeeAnnualSalary(int employeeId) {
        return findEmployee(employeeId)
                .map(emp -> emp.getSalary() * 12)
                .orElse(-1.0);
    }

    // 부서의 직원들 평균 연봉 계산, 직원이 없으면 -1 반환
    public double getDepartmentAverageAnnualSalary(int deptId) {
        List<Employee> emps = dao.getEmployeesByDepartment(deptId);
        if (emps.isEmpty()) return -1;
        double total = emps.stream().mapToDouble(e -> e.getSalary() * 12).sum();
        return total / emps.size();
    }

    // 퇴직금 계산: 최근 3달치 연봉의 80% 산출, 직원이 없으면 -1 반환
    public double calculateRetirementPay(int employeeId) {
        return findEmployee(employeeId)
                .map(emp -> emp.getSalary() * 3 * 0.8)
                .orElse(-1.0);
    }

    // 연봉 인상 적용: 인상률(%)을 적용하여 급여 수정 후 업데이트, 실패 시 false 반환
    public boolean applySalaryIncrease(int employeeId, double rate) {
        return findEmployee(employeeId)
                .map(emp -> {
                    emp.setSalary(emp.getSalary() * (1 + rate / 100.0));
                    return dao.updateEmployee(emp);
                }).orElse(false);
    }

    // 직종별 직원들의 평균 연봉 계산, 직원이 없으면 -1 반환
    public double getAvgAnnSalJobId(int jobId) {
        List<Employee> emps = dao.getEmployeesByJobId(jobId);
        if (emps.isEmpty()) return -1;
        double total = emps.stream().mapToDouble(e -> e.getSalary() * 12).sum();
        return total / emps.size();
    }

    // 보너스 계산: 월급의 일정 퍼센트(%)를 보너스로 산출
    public double calculateBonus(int employeeId, double performanceFactor) {
        return findEmployee(employeeId)
                .map(emp -> emp.getSalary() * performanceFactor / 100)
                .orElse(0.0);
    }

    // 오버타임 급여 계산: 시간당 급여 산출 후, 추가 근무시간과 1.5배 배율 적용
    public double calculateOvertimePay(int employeeId, double overtimeHours) {
        return findEmployee(employeeId)
                .map(emp -> {
                    double hourlyWage = emp.getSalary() / 160; // 기준 160시간/월
                    return hourlyWage * overtimeHours * 1.5;
                }).orElse(0.0);
    }

    // 급여 이력 조회 (더미 구현, 실제 구현 시 연동 필요)
//    public String getSalaryHistory(int employeeId) {
//        return employeeId + "??";
//    }

    // 전체 직원에 대해 일괄 연봉 인상 적용: 인상률(%) 적용 후 업데이트 성공한 직원 수 반환
    public int applyBatchSalaryIncrease(double percentage) {
        List<Employee> allEmployees = dao.getAllEmployees();
        int updatedCount = 0;
        for (Employee emp : allEmployees) {
            emp.setSalary(emp.getSalary() * (1 + percentage / 100));
            if (dao.updateEmployee(emp)) {
                updatedCount++;
            }
        }
        return updatedCount;
    }

    // 직원 급여 직접 수정: 새로운 급여를 설정하고 업데이트, 실패 시 false 반환
    public boolean updateEmployeeSalary(int employeeId, double newSalary) {
        return findEmployee(employeeId)
                .map(emp -> {
                    emp.setSalary(newSalary);
                    return dao.updateEmployee(emp);
                }).orElse(false);
    }
}
