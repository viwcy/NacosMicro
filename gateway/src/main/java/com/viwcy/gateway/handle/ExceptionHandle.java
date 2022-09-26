//package com.viwcy.gateway.handle;
//
//import com.viwcy.gateway.common.ResultEntity;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.bind.support.WebExchangeBindException;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
// */
//@RestControllerAdvice
//@Slf4j
//public class ExceptionHandle {
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public ResultEntity exceptionHandler(Exception e) {
//
//        log.error("Global catch exception: ", e);
//        return ResultEntity.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器异常");
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(value = NullPointerException.class)
//    public ResultEntity exceptionHandler(NullPointerException e) {
//
//        log.error("Global catch exception: ", e);
//        return ResultEntity.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "NullPointerException");
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler({WebExchangeBindException.class})
//    public ResultEntity exceptionHandler(WebExchangeBindException e) {
//
//        log.error("Global catch exception: ", e);
//        return responseException(e, null);
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public ResultEntity exceptionHandler(MethodArgumentNotValidException e) {
//
//        log.error("Global catch exception: ", e);
//        return responseException(null, e);
//    }
//
//    private final ResultEntity responseException(WebExchangeBindException e, MethodArgumentNotValidException e1) {
//
//        List<FieldError> fieldErrors;
//        if (Objects.isNull(e)) {
//            fieldErrors = e1.getBindingResult().getFieldErrors();
//        } else {
//            fieldErrors = e.getBindingResult().getFieldErrors();
//        }
//        List<String> msgList = fieldErrors.stream()
//                .map(o -> o.getDefaultMessage())
//                .collect(Collectors.toList());
//        String messages = StringUtils.join(msgList.toArray(), ";");
//        return ResultEntity.fail(HttpStatus.BAD_REQUEST.value(), messages);
//    }
//}
