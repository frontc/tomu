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
    private final static Map<BizErrorCode, ErrorResponse> bizError = new HashMap<BizErrorCode, ErrorResponse>(){{
        put(BizErrorCode.CHANNEL_IS_FULL,new ErrorResponse.ErrorResponseBuilder()
                .withCode("4000")
                .withType("ERROR")
                .withMessage("频道已满")
                .build());
        put(BizErrorCode.CHANNEL_NOT_EXISTS,new ErrorResponse.ErrorResponseBuilder()
                .withCode("4001")
                .withType("ERROR")
                .withMessage("频道不存在")
                .build());
        put(BizErrorCode.NO_TOKEN,new ErrorResponse.ErrorResponseBuilder()
                .withCode("4030")
                .withType("ERROR")
                .withMessage("未携带token")
                .build());
        put(BizErrorCode.INVALID_TOKEN,new ErrorResponse.ErrorResponseBuilder()
                .withCode("4031")
                .withType("ERROR")
                .withMessage("token无效")
                .build());
    }};

    public static ErrorResponse generate(BizErrorCode bizErrorCode){
        return bizError.get(bizErrorCode);
    }
}
