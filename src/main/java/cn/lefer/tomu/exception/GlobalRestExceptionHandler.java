package cn.lefer.tomu.exception;

import cn.lefer.tomu.constant.BizError;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/22
 * @Description : 全局接口异常封装处理
 */
@RestControllerAdvice
public class GlobalRestExceptionHandler {
    @ExceptionHandler({TypeMismatchException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(Exception ex){
        ErrorResponse errorResponse = new ErrorResponse.ErrorResponseBuilder()
                .withCode("4220")
                .withType("ERROR")
                .withMessage("参数类型不匹配")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({BizRestException.class})
    protected ResponseEntity<Object> bizRestExceptionHandle(BizRestException ex){
        ErrorResponse errorResponse = BizError.bizError.get(ex.bizErrorCode);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
