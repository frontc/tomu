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
                .withMessage("Argument Type MisMatch.Please Check Your Parameters's Type.")
                .build());
        put(BasicErrorCode.ARGUMENT_VALUE_INVALID, new ErrorResponse.ErrorResponseBuilder()
                .withCode("4221")
                .withType("ERROR")
                .withMessage("Argument Value Invalid.Please Check The Value You Input.")
                .build());
        put(BasicErrorCode.METHOD_NOT_ALLOWED, new ErrorResponse.ErrorResponseBuilder()
                .withCode("4050")
                .withType("ERROR")
                .withMessage("Http Method Not Allowed.Please Read Tomu API Doc.")
                .build());
        put(BasicErrorCode.NOT_FOUND, new ErrorResponse.ErrorResponseBuilder()
                .withCode("4040")
                .withType("ERROR")
                .withMessage("The API What You Want To Visit Isn't Exists.")
                .build());
    }};

    public static ErrorResponse generate(BasicErrorCode errorCode) {
        return bizError.get(errorCode);
    }
}
