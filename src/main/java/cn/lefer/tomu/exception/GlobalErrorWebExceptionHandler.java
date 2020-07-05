package cn.lefer.tomu.exception;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/5
 * @Description : 全局异常处理类
 */
@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes,
                                          ResourceProperties resourceProperties,
                                          ServerCodecConfigurer serverCodecConfigurer,
                                          ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(
                RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(
            ServerRequest request) {
        return ServerResponse.status(getHttpStatus(request))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getErrorResponse(request)));
    }

    private ErrorResponse getErrorResponse(ServerRequest request) {
        Map<String, Object> errorPropertiesMap = getErrorAttributes(request,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.BINDING_ERRORS,
                        ErrorAttributeOptions.Include.EXCEPTION,
                        ErrorAttributeOptions.Include.STACK_TRACE,
                        ErrorAttributeOptions.Include.MESSAGE));
        String exception = errorPropertiesMap.get("exception").toString();
        if (ResponseStatusException.class.getName().equals(exception)) {
            return BasicError.generate(BasicErrorCode.NOT_FOUND);
        } else if (NumberFormatException.class.getName().equals(exception)) {
            return BasicError.generate(BasicErrorCode.ARGUMENT_TYPE_MISMATCH);
        } else if (MethodNotAllowedException.class.getName().equals(exception)) {
            return BasicError.generate(BasicErrorCode.METHOD_NOT_ALLOWED);
        } else {
            return new ErrorResponse.ErrorResponseBuilder().withCode("-1").
                    withType("error").
                    withMessage(errorPropertiesMap.get("message").toString()).build();
        }
    }

    private HttpStatus getHttpStatus(ServerRequest request) {
        Map<String, Object> errorPropertiesMap = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        int status = (int) errorPropertiesMap.get("status");
        return HttpStatus.valueOf(status);
    }
}
