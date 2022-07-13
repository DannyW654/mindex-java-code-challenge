package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.dao.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService{
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation comp){
        LOG.debug("Creating compensation for employee [{}]", comp.getEmployeeId());

        /*
        * Not entirely sure about compensation specifications.
        *
        * For now, assume that only one compensation at a time is available per employee, 
        * and use the id as the identifier.
        */
        compensationRepository.insert(comp);

        return comp;
    }

    public Compensation read(String id){
        LOG.debug("Getting compensation for employee [{}]", id);
        
        Compensation comp = compensationRepository.findByEmployeeId(id);

        if (comp == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return comp;


    }
}