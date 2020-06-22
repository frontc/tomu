package cn.lefer.tomu.exception;

import cn.lefer.tomu.constant.BizError;
import cn.lefer.tomu.constant.BizErrorCode;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/22
 * @Description : 业务异常
 */

public class BizRestException extends RuntimeException{
    BizErrorCode bizErrorCode;

    public BizRestException(BizErrorCode bizErrorCode) {
        this.bizErrorCode=bizErrorCode;
    }
}
