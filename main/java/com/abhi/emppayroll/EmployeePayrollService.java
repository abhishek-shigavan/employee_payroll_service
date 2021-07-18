package com.abhi.emppayroll;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class EmployeePayrollService {
    public enum IOService {DB_IO}
    private List<EmployeePayrollData> empPayrollDataList;
    private EmployeePayrollDBService employeePayrollDBService;

    public EmployeePayrollService() {
        employeePayrollDBService = EmployeePayrollDBService.getInstance();
    }

    public EmployeePayrollService(List<EmployeePayrollData> empPayrollDataList) {
        this();
        this.empPayrollDataList = empPayrollDataList;
    }
    /**
     * This method calls readData() which will retrieve all data of database & returns the same
     *
     * @param ioService
     * @return empPayrollDataList - contains all retrieved data of database
     * @throws SQLException
     */
    public List<EmployeePayrollData> readEmployeeData(IOService ioService) throws SQLException {
        if (ioService.equals(IOService.DB_IO))
            this.empPayrollDataList = employeePayrollDBService.readData();
        return this.empPayrollDataList;
    }
    /**
     * This method returns data of given employee name from EmployeePayrollDataList
     *
     * @param name - name of employee
     * @return employeePayrollData -- data of given employee name
     */
    private EmployeePayrollData getEmployeePayrollData(String name) {
        return this.empPayrollDataList.stream()
                .filter(employeePayrollDataItem -> employeePayrollDataItem.name.equals(name) )
                .findFirst()
                .orElse(null);
    }
    /**
     * This method update salary of given employee by its name
     *
     * @param name - employee name
     * @param salary - employee salary
     * @param ioService
     */
    public void updateEmployeeSalary(String name, double salary, IOService ioService) {
        if (ioService.equals(IOService.DB_IO)) {
            //passing name & salary to perform update on database
            int result = employeePayrollDBService.updateEmployeeData(name, salary);
            if (result == 0) return;
        }
        //checking employee with given name is present or not
        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
        //if present then updating salary
        if (employeePayrollData != null )
            employeePayrollData.salary = salary;
    }
    /**
     * This method checks EmployeePayrollData is in sync with database
     * by matching the data from database & data from EmployeePayrollData
     * of given employee name
     *
     * @param name -- employee name
     * @return true if both data matches
     */
    public boolean checkEmployeePayrollSyncWithDB(String name) {
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
        return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
    }

    public List<EmployeePayrollData> readEmployeePayrollForDateRange(IOService ioService, LocalDate startDate, LocalDate endDate) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getEmployeePayrollForDateRange(startDate, endDate);
        return null;
    }

    public Map<String, Double> readAverageSalaryByGender(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getAverageSalaryByGender();
        return null;
    }

    public Map<String, Double> readSumOfSalaryByGender(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getSumOfSalaryByGender();
        return null;
    }

    public Map<String, Double> readMinOfSalaryByGender(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getMinOfSalaryByGender();
        return null;
    }

    public Map<String, Double> readMaxOfSalaryByGender(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getMaxOfSalaryByGender();
        return null;
    }
}
