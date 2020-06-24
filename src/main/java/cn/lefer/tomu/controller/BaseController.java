package cn.lefer.tomu.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/22
 * @Description : 基础控制器
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class BaseController {
    @Value("${tomu.version}")
    String version;

    //获取当前版本
    @GetMapping(value = "/version")
    public String getVersion() {
        return version;
    }
}
