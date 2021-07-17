package com.abhi.emppayroll;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
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
}
