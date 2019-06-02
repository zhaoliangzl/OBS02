package com.izliang.consumer.admin;


import com.izliang.consumer.service.AdminFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ldap.PagedResultsControl;

@RestController
@RequestMapping(value = "/adlogger")
public class AdLogerController {

    @Autowired
    private AdminFeignService adminFeignService;

    @GetMapping("/list")
    String getList(@RequestParam("page")int page){
        return adminFeignService.LoggerList(page);
    }

}
