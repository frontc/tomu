package cn.lefer.tomu.exception;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/22
 * @Description : 业务异常实体
 */

public class BizRestException extends RuntimeException{
    BizErrorCode bizErrorCode;

    public BizRestException(BizErrorCode bizErrorCode) {
        this.bizErrorCode=bizErrorCode;
    }

    public BizErrorCode getBizErrorCode() {
        return bizErrorCode;
    }

    public void setBizErrorCode(BizErrorCode bizErrorCode) {
        this.bizErrorCode = bizErrorCode;
    }
}
