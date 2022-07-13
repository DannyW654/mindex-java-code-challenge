package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String reportUrl;
    private String JohnLennon, RingoStarr, PeteBest; // the employees for testing.
    private Integer JLReports, RSReports, PBReports; //used for storing expected number of reports
    
    @Autowired
    private ReportingStructureService reportingStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportUrl = "http://localhost:" + port + "/employee/{id}/report";

        JohnLennon = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        RingoStarr = "03aa1462-ffa9-4978-901b-7c001562cf6f";
        PeteBest   = "62c1084e-6e34-4630-93fd-9153afb65309";

        JLReports = 4;
        RSReports = 2;
        PBReports = 0;
    }

    @Test
    public void testGetReports(){
        // test getting number of reports for employee with a nested report.
        ReportingStructure reports = restTemplate.getForEntity(reportUrl, ReportingStructure.class, RingoStarr).getBody();
        assertNotNull(reports.getEmployee());
        assertEquals((reports.getEmployee()).getEmployeeId(), RingoStarr);
        assertEquals(reports.getNumberOfReports(),RSReports);
    }
    
    @Test
    public void testGetNestedReports(){
        // test getting number of reports for employee who has a direct report with their own direct reports.
        ReportingStructure reports = restTemplate.getForEntity(reportUrl, ReportingStructure.class, JohnLennon).getBody();
        assertNotNull(reports.getEmployee());
        assertEquals((reports.getEmployee()).getEmployeeId(), JohnLennon);
        assertEquals(reports.getNumberOfReports(),JLReports);
    }

    @Test
    public void testGetNoReports(){
        // test getting number of reports for employee who has no direct reports.
        ReportingStructure reports = restTemplate.getForEntity(reportUrl, ReportingStructure.class, PeteBest).getBody();
        assertNotNull(reports.getEmployee());
        assertEquals((reports.getEmployee()).getEmployeeId(), PeteBest);
        assertEquals(reports.getNumberOfReports(),PBReports);
    }



}