package cn.lefer.tomu.filter;

import cn.lefer.tomu.cache.OnlineStatus;
import cn.lefer.tomu.exception.BizError;
import cn.lefer.tomu.exception.BizErrorCode;
import cn.lefer.tomu.exception.BizRestException;
import cn.lefer.tomu.utils.TomuUtils;
import cn.lefer.tools.Token.LeferJwt;
import org.springframework.beans.factory.annotation.Autowired;
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
    OnlineStatus onlineStatus;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        String path = request.getPath().value();
        if (path.contains("channel")) {
            String token = TomuUtils.getToken(serverWebExchange);
            //1.如果没有token
            if (token==null) {
                return fail(serverWebExchange, HttpStatus.FORBIDDEN, BizErrorCode.NO_TOKEN);
            }
            //2.如果token无效
            if (!LeferJwt.isValid(token, tokenKey)) {
                return fail(serverWebExchange, HttpStatus.FORBIDDEN, BizErrorCode.INVALID_TOKEN);
            }
            //获取本次请求的ChannelID
            String[] pathList = path.split("/");
            int channelID = -1;
            for (int index = 0; index < pathList.length; index++) {
                if (pathList[index].equals("channel") && index < pathList.length - 1) {
                    channelID = Integer.parseInt(pathList[index + 1]);
                    break;
                }
            }
            //记录访客的频道
            if (channelID > 0) {
                try{
                    onlineStatus.updateOnlineStatus(token, channelID);
                }catch (BizRestException ex){
                    return fail(serverWebExchange,HttpStatus.BAD_REQUEST,ex.getBizErrorCode());
                }
            }
        }
        return webFilterChain.filter(serverWebExchange);
    }

    private Mono<Void> fail(ServerWebExchange serverWebExchange, HttpStatus httpStatus, BizErrorCode bizErrorCode) {
        serverWebExchange.getResponse().setStatusCode(httpStatus);
        serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return serverWebExchange.getResponse().writeWith(Mono.just(serverWebExchange.getResponse().bufferFactory().wrap(BizError.generate(bizErrorCode).toJson().getBytes())));
    }

    @Autowired
    public void setOnlineStatus(OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}
