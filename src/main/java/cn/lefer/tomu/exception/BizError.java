package cn.lefer.tomu.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/22
 * @Description : 业务错误映射关系
 */
public class BizError {
    private final static Map<BizErrorCode, ErrorResponse> bizError = new HashMap<BizErrorCode, ErrorResponse>() {{
        put(BizErrorCode.CHANNEL_IS_FULL, new ErrorResponse.ErrorResponseBuilder()
                .withCode("4000")
                .withType("ERROR")
                .withMessage("This Channel Is Full.")
                .build());
        put(BizErrorCode.CHANNEL_NOT_EXISTS, new ErrorResponse.ErrorResponseBuilder()
                .withCode("4001")
                .withType("ERROR")
                .withMessage("This Channel Is Not Exits.")
                .build());
        put(BizErrorCode.NO_TOKEN, new ErrorResponse.ErrorResponseBuilder()
                .withCode("4030")
                .withType("ERROR")
                .withMessage("Have No Token.You Should Get A Token First.")
                .build());
        put(BizErrorCode.INVALID_TOKEN, new ErrorResponse.ErrorResponseBuilder()
                .withCode("4031")
                .withType("ERROR")
                .withMessage("Token Is Invalid.You Should Get A Token Again.")
                .build());
        put(BizErrorCode.PERSISTENCE_FAILED, new ErrorResponse.ErrorResponseBuilder()
                .withCode("5000")
                .withType("ERROR")
                .withMessage("This Is A Internal Error Caused By DataBase.Please Concat To Admin To Solve This Problem.")
                .build());
    }};

    public static ErrorResponse generate(BizErrorCode bizErrorCode) {
        return bizError.get(bizErrorCode);
    }
}
