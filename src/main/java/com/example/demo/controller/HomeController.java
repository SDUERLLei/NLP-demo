package com.example.demo.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by liulei03 on 2020/8/12.
 */
@Api(value="首页控制器",tags = "首页控制器",description = "Ray")
@Controller
public class HomeController {

    //private final Logger logger = LogUtils.getLogger();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/")
    public String homePage() {
        //logger.debug("日志输出测试debug");
        //logger.error("日志输出测试err");
        //logger.warn("日志输出测试warn");
        logger.info("日志输出测试info");
        return "/page/npl/index.html";
    }
}
