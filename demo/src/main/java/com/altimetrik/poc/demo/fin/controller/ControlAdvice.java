package com.altimetrik.poc.demo.fin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.altimetrik.poc.demo.bean.Response_VO;
import com.altimetrik.poc.demo.exception.DataException;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ControlAdvice {

	@ExceptionHandler(DataException.class)
	public ResponseEntity<Response_VO> notFoundException(final DataException e) {

		Response_VO vo = new Response_VO(e.getErrorCode(), e.getErrorMsg());
		ResponseEntity<Response_VO> entity = new ResponseEntity<Response_VO>(vo, HttpStatus.OK);

		return entity;
	}

}
