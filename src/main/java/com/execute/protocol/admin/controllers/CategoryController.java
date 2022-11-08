package com.execute.protocol.admin.controllers;

import com.execute.protocol.dto.CategoryDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("category")
public class CategoryController {
    @GetMapping("/")
    public String category(){
        return "category/index";
    }
    @GetMapping(value = "/category")
    public String category(ModelMap model){
        return "category/category";
    }
    @ResponseBody
    @PostMapping(value = "/category")
    public long category(@RequestBody CategoryDto categoryDto){
        return 1L;
    }
}
