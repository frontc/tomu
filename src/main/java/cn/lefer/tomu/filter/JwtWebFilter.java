package cn.lefer.tomu.filter;

import cn.lefer.tomu.exception.BizError;
import cn.lefer.tomu.exception.BizErrorCode;
import cn.lefer.tools.Token.LeferJwt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/29
 * @Description : 基于Jwt的过滤器，实现token的颁发及在线状态的记录
 */
@Configuration
public class JwtWebFilter implements WebFilter {
    @Value("${token.key}")
    String tokenKey;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        if(request.getPath().value().contains("channel")){
            String authorization =  request.getHeaders().getFirst("Authorization");
            //1.如果没有token
            if(authorization==null || !authorization.startsWith("Bearer ")){
                return fail(serverWebExchange,HttpStatus.FORBIDDEN,BizErrorCode.NO_TOKEN);
            }
            //2.如果token无效
            String token = authorization.substring(6);
            if(!LeferJwt.isValid(token,tokenKey)){
                return fail(serverWebExchange,HttpStatus.FORBIDDEN,BizErrorCode.INVALID_TOKEN);
            }
            //TODO:实现在线状态监控
        }
        return webFilterChain.filter(serverWebExchange);
    }

    private Mono<Void> fail(ServerWebExchange serverWebExchange,HttpStatus httpStatus,BizErrorCode bizErrorCode){
        serverWebExchange.getResponse().setStatusCode(httpStatus);
        serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return serverWebExchange.getResponse().writeWith(Mono.just(serverWebExchange.getResponse().bufferFactory().wrap(BizError.generate(bizErrorCode).toJson().getBytes())));
    }
}
