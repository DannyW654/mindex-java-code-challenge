package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
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
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest{

    private String compUrl;
    private String JohnLennon; // need an employee for testing acquiring benefits

    @Autowired
    private CompensationService CompensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compUrl = "http://localhost:" + port + "/comp/{id}";

        JohnLennon = "16a596ae-edd3-4847-99fe-c4518e82c86f";
    }

    @Test
    public void testCreateReadCompensation(){
        // create check
        Compensation testComp = new Compensation();
        testComp.setEmployeeId(JohnLennon);
        testComp.setSalary("$21.50");
        testComp.setEffectiveDate(new Date().toString());

        Compensation createdComp = restTemplate.postForEntity(compUrl, testComp, Compensation.class, JohnLennon).getBody();
        assertNotNull(createdComp);
        assertCompEquivalence(createdComp,testComp);

        // get check
        Compensation readComp = restTemplate.getForEntity(compUrl,Compensation.class,JohnLennon).getBody();
        assertNotNull(readComp);
        assertCompEquivalence(readComp,createdComp);
    }

    private static void assertCompEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}