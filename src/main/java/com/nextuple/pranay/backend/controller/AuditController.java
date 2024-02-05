package com.nextuple.pranay.backend.controller;

import com.nextuple.pranay.backend.controller.output.Audit;
import com.nextuple.pranay.backend.service.AuditServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditController {

    @Autowired
    AuditServices auditServices;

    @GetMapping(value = "/audit")
    public ResponseEntity<Audit> audit(){
        return auditServices.audit();
    }

}
