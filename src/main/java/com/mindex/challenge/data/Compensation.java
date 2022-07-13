package com.mindex.challenge.data;

public class Compensation{
    String employeeId;
    String salary;
    String effectiveDate;

    public Compensation(){
    }

    public String getEmployeeId(){
        return employeeId;
    }

    public void setEmployeeId(String id){
        this.employeeId = id;
    }

    public String getSalary(){
        return salary;
    }

    public void setSalary(String salary){
        this.salary = salary;
    }

    public String getEffectiveDate(){
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate){
        this.effectiveDate = effectiveDate;
    }
}