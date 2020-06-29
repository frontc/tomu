package cn.lefer.tomu.exception;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/22
 * @Description : 错误响应封装类
 */
public class ErrorResponse {
    String code;
    String type;
    String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static final class ErrorResponseBuilder {
        String code;
        String type;
        String message;

        public ErrorResponseBuilder() {
        }

        public static ErrorResponseBuilder anApiErrorResponse() {
            return new ErrorResponseBuilder();
        }

        public ErrorResponseBuilder withCode(String code) {
            this.code = code;
            return this;
        }

        public ErrorResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponseBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public ErrorResponse build() {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.code = this.code;
            errorResponse.type = this.type;
            errorResponse.message = this.message;
            return errorResponse;
        }
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String toJson() {
        return "{" +
                "\"code\": \"" + code + "\"," +
                "\"type\": \"" + type + "\"," +
                "\"message\": \"" + message + "\"" +
                " }";
    }
}
