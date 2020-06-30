package cn.lefer.tomu.utils;

import org.springframework.web.server.ServerWebExchange;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/30
 * @Description : Tomu工具类
 */
public class TomuUtils {
    public static String getToken(ServerWebExchange serverWebExchange) {
        String authorization = serverWebExchange.getRequest().getHeaders().getFirst("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        } else {
            return authorization.substring(6);
        }
    }

    //现在的生成逻辑是简化的，就是用token的后7位
    public static String getNickname(String str) {
        return str.substring(str.length() - 8, str.length() - 1);
    }
}
