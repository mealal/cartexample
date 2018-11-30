package net.mealal.cartexample.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    public static final Logger logger = LoggerFactory.getLogger(WebController.class);

    @RequestMapping("/")
    public String home(ModelMap model) {
        return "index";
    }

    @RequestMapping("/view/{page}")
    public String view(@PathVariable("page") final String page) {
        return page;
    }

}
