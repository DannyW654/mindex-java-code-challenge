package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController{
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private CompensationService CompensationService;

    @PostMapping("/comp/{id}")
    public Compensation create(@PathVariable String id, @RequestBody Compensation comp) {
        LOG.debug("Received comp create request for employee [{}]", id);

        return CompensationService.create(comp);
    }

    @GetMapping("/comp/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received comp read request for employee [{}]", id);

        return CompensationService.read(id);
    }
}