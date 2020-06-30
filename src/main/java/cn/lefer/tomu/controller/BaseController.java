package cn.lefer.tomu.controller;

import cn.lefer.tools.Token.LeferJwt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/22
 * @Description : 基础控制器
 */
@RestController
@RequestMapping(value = "/api/v1")
public class BaseController {
    @Value("${tomu.version}")
    String version;
    @Value("${token.key}")
    String tokenKey;
    @Value("${token.ttMillis}")
    long ttMillis;

    //获取当前版本
    @GetMapping(value = "/version")
    public String getVersion() {
        return version;
    }

    @GetMapping(value="/auth")
    public String getToken(ServerWebExchange exchange){
        String hostString = exchange.getRequest().getRemoteAddress().getHostString();
        return LeferJwt.createToken("tomu",
                hostString!=null?hostString:"",
                tokenKey,
                ttMillis);
    }
}
