package com.bao.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: DefaultController
 * @Description:
 * @Author: BaoGaoLang
 * @Date: 2017/12/7 14:58
 */

@Controller
public class DefaultController {

        @GetMapping("/")
        public String home1() {
            return "/home";
        }
        @GetMapping("/home")
        public String home() {
            return "/home";
        }

        @GetMapping("/admin")
        public String admin() {
            return "/admin";
        }

        @GetMapping("/user")
        public String user() {
            return "/user";
        }

        @GetMapping("/about")
        public String about() {
            return "/about";
        }

        @GetMapping("/login")
        public String login() {
            return "/login";
        }

        @GetMapping("/403")
        public String error403() {
            return "/error/403";
        }
}
