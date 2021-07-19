package com.abhi.emppayroll;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static com.abhi.emppayroll.EmployeePayrollService.IOService.DB_IO;

/**
 * EmployeePayrollServiceTest   --  Defining test methods for all the operations performed
 *                                  to check all operations works correct / not
 *
 * @author Abhishek Shigavan
 */
public class EmployeePayrollServiceTest {
    /**
     * Checking retrieved no of records when data is read from database is matching with
     * expected no of records / not
     * @throws SQLException
     */
    @Test
    void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() throws SQLException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeeData(DB_IO);
        Assertions.assertEquals(3, employeePayrollData.size());
    }
    /**
     * Checking database is sync with employee payroll when database is updated
     * Check by matching updated data of database with employee payroll data
     * @throws SQLException
     */
    @Test
    void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() throws SQLException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeeData(DB_IO);
        employeePayrollService.updateEmployeeSalary("Terisa", 3000000.00, DB_IO);
        boolean result = employeePayrollService.checkEmployeePayrollSyncWithDB("Terisa");
        Assertions.assertTrue(result);
    }
    /**
     * Checking retrieved no of records present between given joining date range is matching with
     * expected no of records / not
     * @throws SQLException
     */
    @Test
    void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() throws SQLException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeeData(DB_IO);
        LocalDate startDate = LocalDate.of(2018, 01, 01);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollForDateRange(DB_IO, startDate, endDate);
        Assertions.assertEquals(3, employeePayrollData.size());
    }
    /**
     * Checking retrieved average salary by gender is matching with expected average salary / not
     * @throws SQLException
     */
    @Test
    void givenPayrollData_WhenAverageSalaryRetrieveByGender_ShouldReturnProperValue() throws SQLException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeeData(DB_IO);
        Map<String, Double> averageSalaryByGender = employeePayrollService.readAverageSalaryByGender(DB_IO);
        Assertions.assertTrue(averageSalaryByGender.get("M").equals(2000000.00) &&
                                 averageSalaryByGender.get("F").equals(3000000.00) );
    }
    /**
     * Checking retrieved sum of salary by gender is matching with expected sum / not
     * @throws SQLException
     */
    @Test
    void givenPayrollData_WhenSumOfSalaryRetrieveByGender_ShouldReturnProperValue() throws SQLException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeeData(DB_IO);
        Map<String, Double> sumOfSalaryByGender = employeePayrollService.readSumOfSalaryByGender(DB_IO);
        Assertions.assertTrue(sumOfSalaryByGender.get("M").equals(4000000.00) &&
                sumOfSalaryByGender.get("F").equals(3000000.00) );
    }
    /**
     * Checking retrieved min salary by gender is matching with expected salary / not
     * @throws SQLException
     */
    @Test
    void givenPayrollData_WhenMinOfSalaryRetrieveByGender_ShouldReturnProperValue() throws SQLException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeeData(DB_IO);
        Map<String, Double> minOfSalaryByGender = employeePayrollService.readMinOfSalaryByGender(DB_IO);
        Assertions.assertTrue(minOfSalaryByGender.get("M").equals(1000000.00) &&
                                minOfSalaryByGender.get("F").equals(3000000.00) );
    }
    /**
     * Checking retrieved max salary by gender is matching with expected salary / not
     * @throws SQLException
     */
    @Test
    void givenPayrollData_WhenMaxOfSalaryRetrieveByGender_ShouldReturnProperValue() throws SQLException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeeData(DB_IO);
        Map<String, Double> maxOfSalaryByGender = employeePayrollService.readMaxOfSalaryByGender(DB_IO);
        Assertions.assertTrue(maxOfSalaryByGender.get("M").equals(3000000.00) &&
                                maxOfSalaryByGender.get("F").equals(3000000.00) );
    }
    /**
     * Adding new entry into database & checking after adding employee payroll is in sync with
     * database / not
     *
     * @throws SQLException
     */
    @Test
    void givenNewEmployee_WhenAdded_ShouldSyncWithDB() throws SQLException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeeData(DB_IO);
        employeePayrollService.addEmployeeToPayroll("Mark", "M",5000000.00, LocalDate.now());
        boolean result = employeePayrollService.checkEmployeePayrollSyncWithDB("Mark");
        Assertions.assertTrue(result);
    }
}
