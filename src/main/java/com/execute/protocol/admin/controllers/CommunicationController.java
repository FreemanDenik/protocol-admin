package com.execute.protocol.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("communication")
public class CommunicationController {
    @GetMapping("/")
    public String index(ModelMap model, Principal principal){
        model.addAttribute("name", principal.getName());


        return "communication/index";
    }
    @GetMapping("/create")
    public String create(ModelMap model){


        return "communication/create";
    }
    @GetMapping("/particle/{particle}")
    public String particle(@PathVariable String particle){
        return "communication/particle/"+particle+".html";
    }
}
