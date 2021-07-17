package com.abhi.emppayroll;

import java.sql.SQLException;
import java.util.List;

public class EmployeePayrollService {
    public enum IOService {DB_IO}
    private List<EmployeePayrollData> empPayrollDataList;

    public EmployeePayrollService() {
    }

    public EmployeePayrollService(List<EmployeePayrollData> empPayrollDataList) {
        this.empPayrollDataList = empPayrollDataList;
    }

    public List<EmployeePayrollData> readEmployeeData(IOService ioService) throws SQLException {
        if (ioService.equals(IOService.DB_IO))
            this.empPayrollDataList = new EmployeePayrollDBService().readData();
        return this.empPayrollDataList;
    }
}
