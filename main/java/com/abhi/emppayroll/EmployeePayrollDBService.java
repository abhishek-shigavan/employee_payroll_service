package com.abhi.emppayroll;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * EmployeePayrollDBService --  Defining methods to perform CRUD operations on database
 *
 * @author Abhishek Shigavan
 */
public class EmployeePayrollDBService {
    /**
     * This method establish connection with database & returns the same
     *
     * @return connection -- database connection
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
        String userName = "root";
        String password = "Root@1998";
        Connection connection;
        System.out.println("Connecting to Database: "+jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection is successful...." +connection);
        return connection;
    }
    /**
     * This method 1st establish database connection through getConnection() then pass the sql query
     * to execute & store the result in resultSet
     * Fetch the resultSet data & assign it as EmployeePayrollData properties
     * & store it in list & returns the list
     *
     * @return employeePayrollList -- contains output result of query
     */
    public List<EmployeePayrollData> readData() {
        String sql = "select * from employee_payroll";
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }
}
