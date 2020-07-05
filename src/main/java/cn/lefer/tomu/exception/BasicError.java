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
    }};

    public static ErrorResponse generate(BasicErrorCode errorCode) {
        return bizError.get(errorCode);
    }
}
