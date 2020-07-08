package cn.lefer.tomu.exception;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/22
 * @Description : 业务错误枚举
 */
public enum BizErrorCode {
    CHANNEL_IS_FULL,//4000
    CHANNEL_NOT_EXISTS,//4001
    NO_TOKEN,//4030
    INVALID_TOKEN,//4031
    PERSISTENCE_FAILED,//5000
    URL_INVALID,//4060
    URL_TEST_FAILED,//4061
    URL_CONNECTION_TIME_OUT,//4062
}
