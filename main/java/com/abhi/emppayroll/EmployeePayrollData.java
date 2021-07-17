package com.abhi.emppayroll;

import java.time.LocalDate;

/**
 * EmployeePayrollData  --  Defining properties of structure present in database to store employee payroll data
 *
 * @author Abhishek Shigavan
 */
public class EmployeePayrollData {
    //defining employee payroll field
    public int id;
    public String name;
    public double salary;
    public LocalDate startDate;

    public EmployeePayrollData(int id, String name, double salary, LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "EmployeePayrollData{" +
                "Id = " + id +
                ", Name ='" + name + '\'' +
                ", Salary = " + salary +
                ", Start Date = " + startDate +
                '}';
    }
    /**
     * This method checks object values are same / not
     *
     * @param obj
     * @return true if objects values are same
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EmployeePayrollData that = (EmployeePayrollData) obj;
        return id == that.id &&
                Double.compare(that.salary, salary) == 0 &&
                name.equals(that.name);
    }
}
