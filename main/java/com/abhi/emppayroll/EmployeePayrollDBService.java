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
    private PreparedStatement employeePayrollDataStatement;
    private  static  EmployeePayrollDBService employeePayrollDBService;

    private EmployeePayrollDBService() {}

    public static EmployeePayrollDBService getInstance(){
        if (employeePayrollDBService == null)
            employeePayrollDBService = new EmployeePayrollDBService();
        return employeePayrollDBService;
    }
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
            employeePayrollList = this.getEmployeePayrollData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }
    /**
     * This method pass given i/p as a parameter to prepared statement & returns the result
     * of prepared statement
     *
     * @param name - employee name
     * @return employeePayrollList -- result of prepared statement
     */
    public List<EmployeePayrollData> getEmployeePayrollData(String name) {
        List<EmployeePayrollData> employeePayrollList = null;
        if (this.employeePayrollDataStatement == null)
            this.preparedStatementForEmployeeData();
        try{
            //setting value of prepared statements parameter
            employeePayrollDataStatement.setString(1,name);
            ResultSet resultSet = employeePayrollDataStatement.executeQuery();
            employeePayrollList = this.getEmployeePayrollData(resultSet);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return employeePayrollList;
    }
    /**
     * This method is iterating through resultSet & assigning values as properties of EmployeePayrollData
     * & store this into list then returns the list
     *
     * @param resultSet - data retrieved from database
     * @return employeePayrollList - list containing object having payroll data as properties
     */
    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try{
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return employeePayrollList;
    }
    /**
     * This method prepare prepared statement & keeps it in memory
     */
    private void preparedStatementForEmployeeData() {
        try{
            Connection connection = this.getConnection();
            String sql = "select * from employee_payroll where name = ?";
            employeePayrollDataStatement = connection.prepareStatement(sql);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int updateEmployeeData(String name, double salary) {
        return this.updateEmployeeDataUsingPreparedStatement(name, salary);
    }
    /**
     * This method executes query to update the salary of employee by their name
     *
     * @param name -- employee name
     * @param salary -- employee salary
     * @return number of rows affected by query
     */
    private int updateEmployeeDataUsingPreparedStatement(String name, double salary) {
        String sql = String.format("update employee_payroll set salary = %.2f where name = '%s';", salary, name);
        try(Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
}
