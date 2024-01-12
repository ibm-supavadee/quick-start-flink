package org.myorg.quickstart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
    private final String employeeName;
    private String employeeId;

    @JsonCreator
    public Employee(@JsonProperty("name") String name) {
        this.employeeName = name;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
