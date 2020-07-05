package cn.lefer.tomu.exception;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/22
 * @Description : 业务异常实体
 */

public class BasicRestException extends RuntimeException {
    BasicErrorCode basicErrorCode;

    public BasicRestException(BasicErrorCode basicErrorCode) {
        this.basicErrorCode = basicErrorCode;
    }

    public BasicErrorCode getBasicErrorCode() {
        return basicErrorCode;
    }

    public void setBasicErrorCode(BasicErrorCode basicErrorCode) {
        this.basicErrorCode = basicErrorCode;
    }
}
