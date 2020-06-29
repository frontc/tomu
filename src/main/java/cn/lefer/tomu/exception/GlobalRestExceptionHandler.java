package cn.lefer.tomu.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/22
 * @Description : 全局接口异常处理
 */
@RestControllerAdvice
public class GlobalRestExceptionHandler {
    @ExceptionHandler({TypeMismatchException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(Exception ex){
        return new ResponseEntity<>(BasicError.generate(BasicErrorCode.ARGUMENT_TYPE_MISMATCH), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({BizRestException.class})
    protected ResponseEntity<Object> bizRestExceptionHandle(BizRestException ex){
        switch (ex.bizErrorCode){
            case NO_TOKEN:
                return new ResponseEntity<>(BizError.generate(ex.bizErrorCode), HttpStatus.FORBIDDEN);
            default:
                return new ResponseEntity<>(BizError.generate(ex.bizErrorCode), HttpStatus.BAD_REQUEST);
        }
    }
}
