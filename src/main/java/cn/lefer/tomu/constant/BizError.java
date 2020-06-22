package cn.lefer.tomu.constant;

import cn.lefer.tomu.exception.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/22
 * @Description : 业务错误映射关系
 */
public class BizError {
    public final static Map<BizErrorCode, ErrorResponse> bizError = new HashMap<BizErrorCode, ErrorResponse>(){{
        put(BizErrorCode.CHANNEL_IS_FULL,new ErrorResponse.ErrorResponseBuilder()
                .withCode("3000")
                .withType("ERROR")
                .withMessage("频道已满")
                .build());
    }};
}
