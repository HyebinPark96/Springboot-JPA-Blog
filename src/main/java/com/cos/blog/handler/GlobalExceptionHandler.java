package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;

@ControllerAdvice // 모든 exception 발생 시 해당 클래스로 이동됨
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class) // IllegalArgumentException.class일 경우 IllegalArgumentException 발생 시에만 아래 함수 호출됨
	public ResponseDto<String> handleArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
}