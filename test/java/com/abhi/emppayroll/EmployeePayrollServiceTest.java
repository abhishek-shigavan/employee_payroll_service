package com.abhi.emppayroll;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static com.abhi.emppayroll.EmployeePayrollService.IOService.DB_IO;

/**
 * EmployeePayrollServiceTest   --  Defining test methods for all the operations performed
 *                                  to check all operations works correct / not
 *
 * @author Abhishek Shigavan
 */
public class EmployeePayrollServiceTest {

    @Test
    void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() throws SQLException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeeData(DB_IO);
        Assertions.assertEquals(3, employeePayrollData.size());
    }

    @Test
    void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() throws SQLException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeeData(DB_IO);
        employeePayrollService.updateEmployeeSalary("Terisa", 3000000.00, DB_IO);
        boolean result = employeePayrollService.checkEmployeePayrollSyncWithDB("Terisa");
        Assertions.assertTrue(result);
    }

    @Test
    void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() throws SQLException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeeData(DB_IO);
        LocalDate startDate = LocalDate.of(2018, 01, 01);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollForDateRange(DB_IO, startDate, endDate);
        Assertions.assertEquals(3, employeePayrollData.size());
    }
}
