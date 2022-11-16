package com.execute.protocol.admin.controllers;

import com.execute.protocol.dto.ThingDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("thing")
public class ThingController {
    @GetMapping("/")
    public String thing(){
        return "thing/index";
    }
    @GetMapping(value = "/thing")
    public String thing(ModelMap model){
        return "thing/thing";
    }
    @ResponseBody
    @PostMapping(value = "/thing")
    public long thing(@RequestBody ThingDto thingDto){
        return 1L;
    }
}
