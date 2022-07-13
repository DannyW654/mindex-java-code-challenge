package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService{
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure getReports(String employeeId){

        LOG.debug("Getting reports for employee with id [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        Integer numberOfReports = 0;
        List<Employee> reports = employee.getDirectReports();
        //use a breadth-first method to traverse the reports
        while (reports != null && reports.size() != 0){
            Employee report = reports.remove(0);
            numberOfReports++;
            // the report employee object only has an id, so we need a query to get its true data
            report = employeeRepository.findByEmployeeId(report.getEmployeeId());
            if (report.getDirectReports() != null){
                reports.addAll(report.getDirectReports());
            }
        }

        //return the result in the appropriate object.
        ReportingStructure result = new ReportingStructure();
        result.setEmployee(employee);
        result.setNumberOfReports(numberOfReports);
        return result;
    }
}