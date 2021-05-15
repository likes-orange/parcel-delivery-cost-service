package xyz.mynt.parceldeliverycost.controller;

import brave.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xyz.mynt.parceldeliverycost.constant.ErrorMessages;
import xyz.mynt.parceldeliverycost.constant.ResponseCode;
import xyz.mynt.parceldeliverycost.dto.BaseResponse;
import xyz.mynt.parceldeliverycost.dto.ErrorResponse;
import xyz.mynt.parceldeliverycost.exception.BadRequestException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    private final Tracer tracer;

    @ExceptionHandler(Exception.class)
    private ResponseEntity<BaseResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.builder()
                        .error(ErrorResponse.builder()
                                .responseCode(ResponseCode.PDC50000)
                                .messages(Collections.singletonList(ErrorMessages.GENERAL))
                                .timestamp(LocalDateTime.now())
                                .traceId(tracer.currentSpan().context().traceIdString())
                                .build())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.builder()
                        .error(ErrorResponse.builder()
                                .responseCode(ResponseCode.PDC40001)
                                .messages(e.getBindingResult().getFieldErrors()
                                        .stream()
                                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                        .collect(Collectors.toList()))
                                .timestamp(LocalDateTime.now())
                                .traceId(tracer.currentSpan().context().traceIdString())
                                .build())
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<BaseResponse> handleBadRequestException(BadRequestException e) {
        log.warn(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponse.builder()
                        .error(ErrorResponse.builder()
                                .responseCode(ResponseCode.PDC40001)
                                .messages(Collections.singletonList(e.getMessage()))
                                .timestamp(LocalDateTime.now())
                                .traceId(tracer.currentSpan().context().traceIdString())
                                .build())
                        .build());
    }
}
