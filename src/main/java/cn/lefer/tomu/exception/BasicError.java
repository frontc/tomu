package cn.lefer.tomu.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/22
 * @Description : 业务错误映射关系
 */
public class BasicError {
    private final static Map<BasicErrorCode, ErrorResponse> bizError = new HashMap<BasicErrorCode, ErrorResponse>() {{
        put(BasicErrorCode.ARGUMENT_TYPE_MISMATCH, new ErrorResponse.ErrorResponseBuilder()
                .withCode("4220")
                .withType("ERROR")
                .withMessage("参数类型不匹配")
                .build());
        put(BasicErrorCode.ARGUMENT_VALUE_INVALID, new ErrorResponse.ErrorResponseBuilder()
                .withCode("4221")
                .withType("ERROR")
                .withMessage("参数值无效")
                .build());
        put(BasicErrorCode.METHOD_NOT_ALLOWED, new ErrorResponse.ErrorResponseBuilder()
                .withCode("4050")
                .withType("ERROR")
                .withMessage("HTTP Method不支持，请查阅Api文档")
                .build());
        put(BasicErrorCode.NOT_FOUND, new ErrorResponse.ErrorResponseBuilder()
                .withCode("4040")
                .withType("ERROR")
                .withMessage("不存在的API")
                .build());
    }};

    public static ErrorResponse generate(BasicErrorCode errorCode) {
        return bizError.get(errorCode);
    }
}
